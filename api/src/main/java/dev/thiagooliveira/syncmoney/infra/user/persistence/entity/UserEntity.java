package dev.thiagooliveira.syncmoney.infra.user.persistence.entity;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserCreatedEvent;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  private UUID organizationId;

  public UserEntity() {}

  public static UserEntity create(CreateUserInput input, Organization organization) {
    var user = User.create(input.email(), input.name(), organization);
    var entity = new UserEntity();
    entity.id = user.getId();
    entity.email = user.getEmail();
    entity.name = user.getName();
    entity.password = input.password();
    entity.createdAt = user.getCreatedAt();
    entity.organizationId = organization.getId();
    return entity;
  }

  public User toUser() {
    return User.restore(this.id, this.email, this.name, this.createdAt, this.organizationId);
  }

  public UserWithPassword toUserWithPassword() {
    return UserWithPassword.restore(
        this.id, this.email, this.name, this.password, this.createdAt, this.organizationId);
  }

  public User toUserCreated() {
    var user = toUser();
    user.registerEvent(
        new UserCreatedEvent(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt()));
    return user;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganization(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
