package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class AccountSummaryServiceProxy implements AccountSummaryService {

  private final AccountSummaryService accountSummaryService;

  public AccountSummaryServiceProxy(AccountSummaryService accountSummaryService) {
    this.accountSummaryService = accountSummaryService;
  }

  @Transactional
  @Override
  public AggregateAccountSummary getAggregateSummary(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.accountSummaryService.getAggregateSummary(organizationId, accountIds, yearMonth);
  }
}
