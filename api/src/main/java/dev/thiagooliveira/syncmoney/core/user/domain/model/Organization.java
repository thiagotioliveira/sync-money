package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.OrganizationCreatedEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Organization {

  private final UUID id;
  private final OffsetDateTime createdAt;
  private final String emailOwner;

  private Organization(UUID id, OffsetDateTime createdAt, String emailOwner) {
    this.id = id;
    this.createdAt = createdAt;
    this.emailOwner = emailOwner;
  }

  public static Organization create(String emailOwner) {
    var o = new Organization(UUID.randomUUID(), OffsetDateTime.now(), emailOwner);
    DomainEventPublisher.addEvent(
        new OrganizationCreatedEvent(o.getId(), o.getEmailOwner(), o.getCreatedAt()));
    return o;
  }

  public static Organization restore(UUID id, OffsetDateTime createdAt, String emailOwner) {
    return new Organization(id, createdAt, emailOwner);
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
}
