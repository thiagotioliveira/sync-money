package dev.thiagooliveira.syncmoney.core.transaction.application.service.impl;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetAccountSummary;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class AccountSummaryServiceImpl implements AccountSummaryService {

  private final GetAccountSummary getAccountSummary;

  public AccountSummaryServiceImpl(GetAccountSummary getAccountSummary) {
    this.getAccountSummary = getAccountSummary;
  }

  @Override
  public AggregateAccountSummary getAggregateSummary(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.getAccountSummary.execute(organizationId, accountIds, yearMonth);
  }
}
