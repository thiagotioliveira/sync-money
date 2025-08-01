package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserRegisteredEvent implements Event {
  private final UUID id;
  private final String email;
  private final String name;
  private final OffsetDateTime createdAt;

  public UserRegisteredEvent(UUID id, String email, String name, OffsetDateTime createdAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }
}
