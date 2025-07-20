package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.ScheduledTransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface ScheduledTransactionPort {

  ScheduledTransaction save(ScheduledTransaction scheduledTransaction);

  ScheduledTransactionTemplate createTemplate(CreateScheduledTransactionInput input);

  List<ScheduledTransactionTemplate> findTemplatesByAccountId(UUID accountId, YearMonth yearMonth);

  List<ScheduledTransactionEnriched> findByAccountId(UUID accountId, YearMonth yearMonth);

  boolean existsByTemplateIdAndDueDateOriginal(UUID templateId, LocalDate dueDateOriginal);
}
