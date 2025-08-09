package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserWithPassword implements AggregateRoot {
  private UUID id;
  private String email;
  private String name;
  private String password;
  private OffsetDateTime createdAt;
  private UUID organizationId;

  private UserWithPassword() {}

  public static UserWithPassword create(Organization organization, RegisterUserInput input) {
    var user = new UserWithPassword();
    user.id = UUID.randomUUID();
    user.email = input.email();
    user.name = input.name();
    user.password = input.password();
    user.createdAt = organization.getCreatedAt();
    user.organizationId = organization.getId();
    return user;
  }

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

  public User toUser() {
    return User.restore(id, email, name, createdAt, organizationId);
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
