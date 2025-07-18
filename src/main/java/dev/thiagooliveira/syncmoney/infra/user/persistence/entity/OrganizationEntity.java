package dev.thiagooliveira.syncmoney.infra.user.persistence.entity;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateOrganizationInput;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "organizations")
public class OrganizationEntity {

  @Id private UUID id;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "email_owner", nullable = false)
  private String emailOwner;

  public OrganizationEntity() {}

  public OrganizationEntity(UUID id, OffsetDateTime createdAt, String emailOwner) {
    this.id = id;
    this.createdAt = createdAt;
    this.emailOwner = emailOwner;
  }

  public static OrganizationEntity from(CreateOrganizationInput input) {
    OrganizationEntity entity = new OrganizationEntity();
    entity.setId(UUID.randomUUID());
    entity.setCreatedAt(OffsetDateTime.now());
    entity.setEmailOwner(input.emailOwner());
    return entity;
  }

  public Organization toOrganization() {
    return new Organization(id, createdAt, emailOwner);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getEmailOwner() {
    return emailOwner;
  }

  public void setEmailOwner(String emailOwner) {
    this.emailOwner = emailOwner;
  }
}
