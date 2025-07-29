package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.time.YearMonth;
import java.util.UUID;

public interface AccountSummaryService {

  AccountSummary getSummary(UUID organizationId, UUID accountId, YearMonth yearMonth);
}
