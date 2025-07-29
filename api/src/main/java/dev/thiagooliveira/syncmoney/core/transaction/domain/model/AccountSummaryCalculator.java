package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public class AccountSummaryCalculator {

  private final GetTransaction getTransaction;
  private final CreateAccountSummary createAccountSummary;
  private final AccountSummaryRepository accountSummaryRepository;

  public AccountSummaryCalculator(
      GetTransaction getTransaction,
      CreateAccountSummary createAccountSummary,
      AccountSummaryRepository accountSummaryRepository) {
    this.getTransaction = getTransaction;
    this.createAccountSummary = createAccountSummary;
    this.accountSummaryRepository = accountSummaryRepository;
  }

  public AccountSummary calculate(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    var lastSummary =
        this.accountSummaryRepository.findLastOne(accountId).orElseThrow(); // TODO criar um zerado
    this.accountSummaryRepository.deleteTheFuture(accountId, lastSummary.getYearMonth());

    if (lastSummary.getYearMonth().isAfter(yearMonth))
      return AccountSummary.create(accountId, yearMonth);

    YearMonth from = lastSummary.getYearMonth();
    YearMonth to = yearMonth;
    boolean isFirst = true;
    while (from.isBefore(to) || from.equals(to)) {
      final var date = from;
      lastSummary =
          this.createAccountSummary.execute(
              new CreateAccountSummaryInput(
                  accountId,
                  date,
                  isFirst ? BigDecimal.ZERO : lastSummary.getClosingBalance(),
                  this.getTransaction.byAccountIdAndYearMonth(organizationId, accountId, date)));
      from = from.plusMonths(1);
      isFirst = false;
    }
    return lastSummary;
  }
}
