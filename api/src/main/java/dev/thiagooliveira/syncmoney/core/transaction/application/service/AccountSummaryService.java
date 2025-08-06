package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface AccountSummaryService {

  AggregateAccountSummary getAggregateSummary(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth);
}
