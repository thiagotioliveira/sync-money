package dev.thiagooliveira.syncmoney.core.user.domain.model.event;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserCreatedEvent implements Event {
  private final UUID id;
  private final String email;
  private final String name;
  private final OffsetDateTime createdAt;

  public UserCreatedEvent(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.createdAt = user.getCreatedAt();
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
