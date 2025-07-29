package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.time.YearMonth;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountSummaryServiceProxy implements AccountSummaryService {

  private final AccountSummaryService accountSummaryService;

  public AccountSummaryServiceProxy(AccountSummaryService accountSummaryService) {
    this.accountSummaryService = accountSummaryService;
  }

  @Transactional
  @Override
  public AccountSummary getSummary(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    return this.accountSummaryService.getSummary(organizationId, accountId, yearMonth);
  }
}
