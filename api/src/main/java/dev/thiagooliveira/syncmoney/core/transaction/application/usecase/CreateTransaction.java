package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import java.time.YearMonth;

public class CreateTransaction {

  private final EventPublisher eventPublisher;
  private final AccountClient accountClient;
  private final GetCategory getCategory;
  private final TransactionRepository transactionRepository;
  private final AccountSummaryCalculator accountSummaryCalculator;

  public CreateTransaction(
      EventPublisher eventPublisher,
      AccountClient accountClient,
      GetCategory getCategory,
      TransactionRepository transactionRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    this.eventPublisher = eventPublisher;
    this.accountClient = accountClient;
    this.getCategory = getCategory;
    this.transactionRepository = transactionRepository;
    this.accountSummaryCalculator = accountSummaryCalculator;
  }

  public Transaction deposit(CreateTransactionInput input) {
    if (!this.accountClient.existsById(input.organizationId(), input.accountId())) {
      throw BusinessLogicException.notFound("account not found");
    }
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.getType().isCredit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    var transactionPaid = this.transactionRepository.create(input, category);

    this.accountSummaryCalculator.calculate(
        input.organizationId(),
        input.accountId(),
        YearMonth.of(
            transactionPaid.getDueDate().getYear(), transactionPaid.getDueDate().getMonth()));

    transactionPaid.getEvents().forEach(this.eventPublisher::publish);
    return transactionPaid;
  }

  public Transaction withdraw(CreateTransactionInput input) {
    if (!this.accountClient.existsById(input.organizationId(), input.accountId())) {
      throw BusinessLogicException.notFound("account not found");
    }
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.getType().isDebit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    var transactionPaid = this.transactionRepository.create(input, category);
    this.accountSummaryCalculator.calculate(
        input.organizationId(),
        input.accountId(),
        YearMonth.of(
            transactionPaid.getDueDate().getYear(), transactionPaid.getDueDate().getMonth()));
    transactionPaid.getEvents().forEach(this.eventPublisher::publish);
    return transactionPaid;
  }
}
