package dev.thiagooliveira.syncmoney.infra.user.persistence.entity;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.OrganizationCreatedEvent;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
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

  public static OrganizationEntity create(CreateOrganizationInput input) {
    OrganizationEntity entity = new OrganizationEntity();
    entity.setId(UUID.randomUUID());
    entity.setCreatedAt(OffsetDateTime.now());
    entity.setEmailOwner(input.emailOwner());
    return entity;
  }

  public Organization toOrganization() {
    return Organization.restore(id, createdAt, emailOwner);
  }

  public Organization toOrganizationCreated() {
    var organization = toOrganization();
    organization.registerEvent(
        new OrganizationCreatedEvent(
            organization.getId(), organization.getEmailOwner(), organization.getCreatedAt()));
    return organization;
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
