package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.ScheduledTransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class GetScheduledTransaction {

  private final CreateScheduledTransaction createScheduledTransaction;
  private final ScheduledTransactionPort scheduledTransactionPort;

  public GetScheduledTransaction(
      CreateScheduledTransaction createScheduledTransaction,
      ScheduledTransactionPort scheduledTransactionPort) {
    this.createScheduledTransaction = createScheduledTransaction;
    this.scheduledTransactionPort = scheduledTransactionPort;
  }

  public List<ScheduledTransactionEnriched> byAccountId(
      UUID organizationId, UUID accountId, YearMonth yearMonth) {
    var templates = this.scheduledTransactionPort.findTemplatesByAccountId(accountId, yearMonth);
    for (var template : templates) {
      var dayBase = template.startDate().getDayOfMonth();
      switch (template.frequency()) {
        case MONTHLY -> {
          var dueDate = yearMonth.atDay(dayBase);
          if (!this.scheduledTransactionPort.existsByTemplateIdAndDueDateOriginal(
              template.id(), dueDate)) {
            this.createScheduledTransaction.withTemplate(organizationId, template, dueDate);
          }
        }
      }
    }
    return this.scheduledTransactionPort.findByAccountId(accountId, yearMonth);
  }
}
