package dev.thiagooliveira.syncmoney.core.transaction.domain.port.income;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateFirstAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.time.YearMonth;

public class AccountEventListener {

  private static final String INITIAL_BALANCE_DESCRIPTION = "Initial Balance";

  private final EventPublisher eventPublisher;
  private final GetCategory getCategory;
  private final CreateTransaction createTransaction;
  private final AccountSummaryService accountSummaryService;

  public AccountEventListener(
      EventPublisher eventPublisher,
      GetCategory getCategory,
      CreateTransaction createTransaction,
      AccountSummaryService accountSummaryService) {
    this.eventPublisher = eventPublisher;
    this.getCategory = getCategory;
    this.createTransaction = createTransaction;
    this.accountSummaryService = accountSummaryService;
  }

  public void on(AccountCreatedEvent event) {
    var initialBalance = event.getInitialBalance();
    var isCredit = initialBalance.signum() > 0;
    this.accountSummaryService.createFirstSummary(
        new CreateFirstAccountSummaryInput(event.getId(), YearMonth.from(event.getDateTime())));

    var type = isCredit ? CategoryType.CREDIT : CategoryType.DEBIT;
    var category =
        getCategory
            .getDefaultByType(type)
            .orElseThrow(
                () -> BusinessLogicException.notFound("category not found for type:" + type));

    var input =
        new CreateTransactionInput(
            event.getId(),
            event.getOrganizationId(),
            event.getUserId(),
            event.getDateTime(),
            INITIAL_BALANCE_DESCRIPTION,
            category.getId(),
            initialBalance);

    Transaction transaction = null;
    if (isCredit) {
      transaction = createTransaction.deposit(input);
    } else {
      transaction = createTransaction.withdraw(input);
    }
    DomainEventPublisher.publish(this.eventPublisher::publish);
  }
}
