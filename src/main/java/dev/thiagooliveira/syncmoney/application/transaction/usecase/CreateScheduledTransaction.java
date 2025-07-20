package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.category.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.ScheduledTransactionCreatedEvent;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionStatus;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class CreateScheduledTransaction {

  private final EventPublisher eventPublisher;
  private final GetAccount getAccount;
  private final GetCategory getCategory;
  private final ScheduledTransactionPort scheduledTransactionPort;

  public CreateScheduledTransaction(
      EventPublisher eventPublisher,
      GetAccount getAccount,
      GetCategory getCategory,
      ScheduledTransactionPort scheduledTransactionPort) {
    this.eventPublisher = eventPublisher;
    this.getAccount = getAccount;
    this.getCategory = getCategory;
    this.scheduledTransactionPort = scheduledTransactionPort;
  }

  public ScheduledTransaction execute(CreateScheduledTransactionInput input) {
    var account =
        this.getAccount
            .findById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    var category =
        this.getCategory
            .findById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));

    if (category.isCredit() && input.type().isPayable()) {
      throw BusinessLogicException.badRequest("payable credit transaction is not supported");
    }
    if (category.isDebit() && input.type().isReceivable()) {
      throw BusinessLogicException.badRequest("receivable debit transaction is not supported");
    }
    if (LocalDate.now().isAfter(input.startDate())) {
      throw BusinessLogicException.badRequest("start date needs to be after today");
    }
    if (input.installmentTotal().isPresent() && input.installmentTotal().get() <= 0) {
      throw BusinessLogicException.badRequest("installment total must be greater than zero");
    }
    if (input.isAmountGraterThan(BigDecimal.ZERO)) {
      throw BusinessLogicException.badRequest("amount must be greater than zero");
    }
    var template = this.scheduledTransactionPort.createTemplate(input);
    var installments = template.generateInstallments();
    installments.forEach(this.scheduledTransactionPort::save);
    this.eventPublisher.publish(new ScheduledTransactionCreatedEvent(template, installments));
    return installments.get(0);
  }

  public ScheduledTransaction withTemplate(
      UUID organizationId, ScheduledTransactionTemplate template, LocalDate dueDate) {
    var category =
        this.getCategory
            .findById(organizationId, template.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    return this.scheduledTransactionPort.save(
        new ScheduledTransaction(
            UUID.randomUUID(),
            template.id(),
            template.accountId(),
            template.categoryId(),
            template.description(),
            template.amount(),
            dueDate,
            template.type(),
            ScheduledTransactionStatus.SCHEDULED,
            template.frequency(),
            Optional.empty(),
            template.installmentTotal(),
            Optional.empty()));
  }
}
