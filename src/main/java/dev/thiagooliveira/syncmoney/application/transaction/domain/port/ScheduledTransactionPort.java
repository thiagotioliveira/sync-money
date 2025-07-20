package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;

public interface ScheduledTransactionPort {

  ScheduledTransaction save(ScheduledTransaction scheduledTransaction);

  ScheduledTransactionTemplate createTemplate(CreateScheduledTransactionInput input);
}
