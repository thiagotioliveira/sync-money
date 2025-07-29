package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.time.LocalDate;
import java.util.UUID;

public class PayableReceivableCreatedEvent implements Event {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean recurring;

  public PayableReceivableCreatedEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      LocalDate startDate,
      LocalDate endDate,
      boolean recurring) {
    this.accountId = accountId;
    this.endDate = endDate;
    this.id = id;
    this.organizationId = organizationId;
    this.recurring = recurring;
    this.startDate = startDate;
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
