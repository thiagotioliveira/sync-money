package dev.thiagooliveira.syncmoney.application.user.domain;

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
    user.createdAt = organization.createdAt();
    user.organizationId = organization.id();
    return user;
  }

  public static User from(
      UUID id, String email, String name, OffsetDateTime createdAt, UUID organizationId) {
    var user = new User();
    user.id = id;
    user.email = email;
    user.name = name;
    user.createdAt = createdAt;
    user.organizationId = organizationId;
    return user;
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
