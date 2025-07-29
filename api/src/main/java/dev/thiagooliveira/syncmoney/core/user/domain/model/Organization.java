package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Organization extends AggregateRoot {

  private UUID id;
  private OffsetDateTime createdAt;
  private String emailOwner;

  private Organization(UUID id, OffsetDateTime createdAt, String emailOwner) {
    this.id = id;
    this.createdAt = createdAt;
    this.emailOwner = emailOwner;
  }

  public static Organization create(String emailOwner) {
    return new Organization(UUID.randomUUID(), OffsetDateTime.now(), emailOwner);
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
