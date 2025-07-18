package dev.thiagooliveira.syncmoney.application.user.event;

import dev.thiagooliveira.syncmoney.application.event.dto.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OrganizationCreatedEvent implements Event {
  private final UUID id;
  private final OffsetDateTime createdAt;
  private final String emailOwner;

  public OrganizationCreatedEvent(UUID id, OffsetDateTime createdAt, String emailOwner) {
    this.id = id;
    this.createdAt = createdAt;
    this.emailOwner = emailOwner;
  }

  public UUID getId() {
    return id;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public String getEmailOwner() {
    return emailOwner;
  }
}
