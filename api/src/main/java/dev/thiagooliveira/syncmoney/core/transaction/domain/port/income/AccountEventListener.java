package dev.thiagooliveira.syncmoney.core.transaction.domain.port.income;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateFirstAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.time.YearMonth;

public class AccountEventListener {

  private static final String INITIAL_BALANCE_DESCRIPTION = "Initial Balance";

  private final GetCategory getCategory;
  private final CreateTransaction createTransaction;
  private final CreateAccountSummary createAccountSummary;

  public AccountEventListener(
      GetCategory getCategory,
      CreateTransaction createTransaction,
      CreateAccountSummary createAccountSummary) {
    this.getCategory = getCategory;
    this.createTransaction = createTransaction;
    this.createAccountSummary = createAccountSummary;
  }

  public void listen(AccountCreatedEvent event) {
    var initialBalance = event.getInitialBalance();
    var isCredit = initialBalance.signum() > 0;
    this.createAccountSummary.execute(
        new CreateFirstAccountSummaryInput(event.getId(), YearMonth.from(event.getCreatedAt())));

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
            event.getCreatedAt(),
            INITIAL_BALANCE_DESCRIPTION,
            category.getId(),
            initialBalance);

    Transaction transaction = null;
    if (isCredit) {
      transaction = createTransaction.deposit(input);
    } else {
      transaction = createTransaction.withdraw(input);
    }
  }
}
