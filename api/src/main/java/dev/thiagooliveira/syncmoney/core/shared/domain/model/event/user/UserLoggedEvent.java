package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserLoggedEvent implements DomainEvent {
  private final UUID userId;
  private final OffsetDateTime dateTime;

  public UserLoggedEvent(UUID userId, OffsetDateTime dateTime) {
    this.userId = userId;
    this.dateTime = dateTime;
  }

  public UUID getUserId() {
    return userId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public String toString() {
    return "UserLoggedEvent{" + "userId=" + userId + ", dateTime=" + dateTime + '}';
  }
}
