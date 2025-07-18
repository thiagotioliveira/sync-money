package dev.thiagooliveira.syncmoney.infra.persistence.entity;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
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

  public static UserEntity from(CreateUserInput input, Organization organization) {
    var user = User.create(input.email(), input.name(), organization);
    var entity = new UserEntity();
    entity.id = user.getId();
    entity.email = user.getEmail();
    entity.name = user.getName();
    entity.password = input.password();
    entity.createdAt = user.getCreatedAt();
    entity.organizationId = organization.id();
    return entity;
  }

  public User toUser() {
    return User.from(this.id, this.email, this.name, this.createdAt, this.organizationId);
  }

  public void update(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
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
