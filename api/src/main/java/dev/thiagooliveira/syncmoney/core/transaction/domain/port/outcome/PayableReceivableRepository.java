package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayableReceivableRepository {

  PayableReceivable create(CreatePayableReceivableInput input);

  PayableReceivable update(PayableReceivable payableReceivable);

  List<PayableReceivable> getRecurringByAccountId(UUID accountId, YearMonth yearMonth);

  Optional<PayableReceivable> getById(UUID organizationId, UUID id);
}
