package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.PayableReceivable;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface PayableReceivablePort {

  PayableReceivable create(CreatePayableReceivableInput input);

  List<PayableReceivable> findRecurringByAccountId(UUID accountId, YearMonth yearMonth);
}
