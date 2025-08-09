package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BankCreatedEvent implements DomainEvent {
  private final UUID id;
  private final UUID organizationId;
  private final String name;
  private final OffsetDateTime dateTime;

  public BankCreatedEvent(UUID id, String name, UUID organizationId, OffsetDateTime dateTime) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "BankCreatedEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", name='"
        + name
        + '\''
        + ", dateTime="
        + dateTime
        + '}';
  }

  public UUID getId() {
    return id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public String getName() {
    return name;
  }

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
