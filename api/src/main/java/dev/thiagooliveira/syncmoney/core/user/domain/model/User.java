package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserLoggedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserRegisteredEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class User {
  private UUID id;
  private String email;
  private String name;
  private OffsetDateTime createdAt;
  private UUID organizationId;

  private User() {}

  public static User create(String email, String name, Organization organization) {
    var user = new User();
    user.id = UUID.randomUUID();
    user.email = email;
    user.name = name;
    user.createdAt = organization.getCreatedAt();
    user.organizationId = organization.getId();
    return user;
  }

  public static User restore(
      UUID id, String email, String name, OffsetDateTime createdAt, UUID organizationId) {
    var user = new User();
    user.id = id;
    user.email = email;
    user.name = name;
    user.createdAt = createdAt;
    user.organizationId = organizationId;
    return user;
  }

  public User registered() {
    DomainEventPublisher.addEvent(
        new UserRegisteredEvent(this.id, this.email, this.name, this.createdAt));
    return this;
  }

  public User authenticated() {
    DomainEventPublisher.addEvent(new UserLoggedEvent(this.id, OffsetDateTime.now()));
    return this;
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

  public UUID getOrganizationId() {
    return organizationId;
  }
}
