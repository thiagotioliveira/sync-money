package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class GetAccountSummary {

  private final AccountClient accountClient;
  private final AccountSummaryCalculator accountSummaryCalculator;

  public GetAccountSummary(
      AccountClient accountClient, AccountSummaryCalculator accountSummaryCalculator) {
    this.accountClient = accountClient;
    this.accountSummaryCalculator = accountSummaryCalculator;
  }

  public AggregateAccountSummary execute(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    accountIds.forEach(
        accountId -> {
          if (!this.accountClient.existsById(organizationId, accountId)) {
            throw BusinessLogicException.notFound("account not found");
          }
        });
    return this.accountSummaryCalculator.calculate(organizationId, accountIds, yearMonth);
  }
}
