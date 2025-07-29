package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.time.LocalDate;
import java.util.UUID;

public class PayableReceivableUpdatedEvent implements Event {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean recurring;

  public PayableReceivableUpdatedEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      LocalDate startDate,
      LocalDate endDate,
      boolean recurring) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.recurring = recurring;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public UUID getId() {
    return id;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }
}
