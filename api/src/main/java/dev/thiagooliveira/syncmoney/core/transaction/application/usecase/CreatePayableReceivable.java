package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.PayableReceivableRepository;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import java.time.YearMonth;

public class CreatePayableReceivable {

  private final GetAccount getAccount;
  private final GetCategory getCategory;
  private final TransactionRepository transactionRepository;
  private final PayableReceivableRepository payableReceivableRepository;
  private final AccountSummaryCalculator accountSummaryCalculator;

  public CreatePayableReceivable(
      GetAccount getAccount,
      GetCategory getCategory,
      TransactionRepository transactionRepository,
      PayableReceivableRepository payableReceivableRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    this.getAccount = getAccount;
    this.getCategory = getCategory;
    this.transactionRepository = transactionRepository;
    this.payableReceivableRepository = payableReceivableRepository;
    this.accountSummaryCalculator = accountSummaryCalculator;
  }

  public PayableReceivable payable(CreatePayableReceivableInput input) {
    var account =
        this.getAccount
            .getById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.isDebit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    var payableReceivable =
        this.payableReceivableRepository.create(input).addPayableReceivableCreatedEvent();

    var installments =
        this.transactionRepository
            .createInstallments(payableReceivable.generateInstallments())
            .stream()
            .map(Installment::addInstallmentCreatedEvent)
            .toList();
    installments.forEach(installment -> payableReceivable.registerEvents(installment.getEvents()));
    var dueDate = installments.get(0).getDueDate();
    this.accountSummaryCalculator.calculate(
        input.organizationId(),
        input.accountId(),
        YearMonth.of(dueDate.getYear(), dueDate.getMonth()));
    return payableReceivable;
  }

  public PayableReceivable receivable(CreatePayableReceivableInput input) {
    var account =
        this.getAccount
            .getById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.isCredit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    var payableReceivable =
        this.payableReceivableRepository.create(input).addPayableReceivableCreatedEvent();

    var installments =
        this.transactionRepository
            .createInstallments(payableReceivable.generateInstallments())
            .stream()
            .map(Installment::addInstallmentCreatedEvent)
            .toList();
    installments.forEach(installment -> payableReceivable.registerEvents(installment.getEvents()));
    var dueDate = installments.get(0).getDueDate();
    this.accountSummaryCalculator.calculate(
        input.organizationId(),
        input.accountId(),
        YearMonth.of(dueDate.getYear(), dueDate.getMonth()));
    return payableReceivable;
  }
}
