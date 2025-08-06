package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateFirstAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class AccountSummaryServiceImpl implements AccountSummaryService {

  private final GetAccountSummary getAccountSummary;
  private final CreateAccountSummary createAccountSummary;

  public AccountSummaryServiceImpl(
      GetAccountSummary getAccountSummary, CreateAccountSummary createAccountSummary) {
    this.getAccountSummary = getAccountSummary;
    this.createAccountSummary = createAccountSummary;
  }

  @Override
  public AggregateAccountSummary getAggregateSummary(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.getAccountSummary.execute(organizationId, accountIds, yearMonth);
  }

  @Override
  public AccountSummary createFirstSummary(CreateFirstAccountSummaryInput input) {
    return this.createAccountSummary.execute(input);
  }
}
