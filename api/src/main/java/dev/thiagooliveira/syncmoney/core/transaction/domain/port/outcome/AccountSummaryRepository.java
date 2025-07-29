package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountSummaryRepository {

  int deleteTheFuture(UUID accountId, YearMonth yearMonth);

  Optional<AccountSummary> findByAccountIdAndYearMonth(UUID accountId, YearMonth yearMonth);

  Optional<AccountSummary> findLastOne(UUID accountId);

  List<AccountSummary> findAllByAccountIdAfter(UUID accountId, YearMonth yearMonth);

  AccountSummary save(AccountSummary accountSummary);
}
