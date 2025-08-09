package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OrganizationCreatedEvent implements DomainEvent {

  private final UUID id;
  private final OffsetDateTime createdAt;
  private final String emailOwner;

  public OrganizationCreatedEvent(UUID id, String emailOwner, OffsetDateTime createdAt) {
    this.id = id;
    this.emailOwner = emailOwner;
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "OrganizationCreatedEvent{"
        + "id="
        + id
        + ", dateTime="
        + createdAt
        + ", emailOwner='"
        + emailOwner
        + '\''
        + '}';
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public String getEmailOwner() {
    return emailOwner;
  }

  public UUID getId() {
    return id;
  }

  @Override
  public OffsetDateTime getDateTime() {
    return this.createdAt;
  }
}
