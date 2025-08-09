package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountSummaryCalculator {

  private final AccountClient accountClient;
  private final GetTransaction getTransaction;
  private final CreateAccountSummary createAccountSummary;
  private final AccountSummaryRepository accountSummaryRepository;

  public AccountSummaryCalculator(
      AccountClient accountClient,
      GetTransaction getTransaction,
      CreateAccountSummary createAccountSummary,
      AccountSummaryRepository accountSummaryRepository) {
    this.accountClient = accountClient;
    this.getTransaction = getTransaction;
    this.createAccountSummary = createAccountSummary;
    this.accountSummaryRepository = accountSummaryRepository;
  }

  public AccountSummary calculate(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    var lastSummary = this.accountSummaryRepository.findLastOne(accountId).orElseThrow();
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

  public AggregateAccountSummary calculate(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    List<AccountSummary> summaries = new ArrayList<>();
    accountIds.stream()
        .parallel()
        .forEach(accountId -> summaries.add(calculate(organizationId, accountId, yearMonth)));

    var map =
        accountIds.stream()
            .map(id -> this.accountClient.getById(organizationId, id).orElseThrow())
            .collect(
                Collectors.toMap(
                    AccountEnriched::id,
                    AccountEnriched::currency,
                    (existing, replacement) -> existing));
    return new AggregateAccountSummary(summaries, map);
  }
}
