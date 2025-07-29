package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;
import java.time.YearMonth;
import java.util.UUID;

public class GetAccountSummary {

  private final AccountClient accountClient;
  private final AccountSummaryRepository accountSummaryRepository;
  private final AccountSummaryCalculator accountSummaryCalculator;

  public GetAccountSummary(
      AccountClient accountClient,
      AccountSummaryRepository accountSummaryRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    this.accountClient = accountClient;
    this.accountSummaryRepository = accountSummaryRepository;
    this.accountSummaryCalculator = accountSummaryCalculator;
  }

  public AccountSummary execute(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    if (!this.accountClient.existsById(organizationId, accountId)) {
      throw BusinessLogicException.notFound("account not found");
    }
    return this.accountSummaryRepository
        .findByAccountIdAndYearMonth(accountId, yearMonth)
        .orElseGet(
            () -> this.accountSummaryCalculator.calculate(organizationId, accountId, yearMonth));
  }
}
