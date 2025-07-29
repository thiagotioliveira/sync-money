package dev.thiagooliveira.syncmoney.core.transaction.application.service.impl;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.time.YearMonth;
import java.util.UUID;

public class AccountSummaryServiceImpl implements AccountSummaryService {

  private final GetAccountSummary getAccountSummary;

  public AccountSummaryServiceImpl(GetAccountSummary getAccountSummary) {
    this.getAccountSummary = getAccountSummary;
  }

  @Override
  public AccountSummary getSummary(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    return this.getAccountSummary.execute(organizationId, accountId, yearMonth);
  }
}
