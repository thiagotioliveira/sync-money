package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ScheduledTransactionCreatedEvent implements Event {
  private final UUID templateId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean enabled;
  private final boolean recurring;
  private final List<ScheduledTransaction> installments;

  public ScheduledTransactionCreatedEvent(
      ScheduledTransactionTemplate template, List<ScheduledTransaction> installments) {
    this.enabled = template.enabled();
    this.templateId = template.id();
    this.startDate = template.startDate();
    this.endDate = template.endDate();
    this.recurring = template.recurring();
    this.installments = installments;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public List<ScheduledTransaction> getInstallments() {
    return installments;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public UUID getTemplateId() {
    return templateId;
  }
}
