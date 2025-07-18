package dev.thiagooliveira.syncmoney.application.user.event;

import dev.thiagooliveira.syncmoney.application.event.dto.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserCreatedEvent implements Event {
  private final UUID id;
  private final String email;
  private final String name;
  private final OffsetDateTime createdAt;

  public UserCreatedEvent(UUID id, String email, String name, OffsetDateTime createdAt) {
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
