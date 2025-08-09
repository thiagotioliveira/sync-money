package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PayableReceivableUpdatedEvent implements DomainEvent {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean recurring;
  private final OffsetDateTime dateTime;

  public PayableReceivableUpdatedEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      LocalDate startDate,
      LocalDate endDate,
      boolean recurring,
      OffsetDateTime dateTime) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.recurring = recurring;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "PayableReceivableUpdatedEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", accountId="
        + accountId
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", recurring="
        + recurring
        + ", dateTime="
        + dateTime
        + '}';
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

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
