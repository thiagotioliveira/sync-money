package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserWithPassword extends AggregateRoot {
  private UUID id;
  private String email;
  private String name;
  private String password;
  private OffsetDateTime createdAt;
  private UUID organizationId;

  private UserWithPassword() {}

  public static UserWithPassword restore(
      UUID id,
      String email,
      String name,
      String password,
      OffsetDateTime createdAt,
      UUID organizationId) {
    var user = new UserWithPassword();
    user.id = id;
    user.email = email;
    user.name = name;
    user.password = password;
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

  public String getPassword() {
    return password;
  }
}
