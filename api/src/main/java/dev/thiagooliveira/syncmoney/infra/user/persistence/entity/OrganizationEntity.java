package dev.thiagooliveira.syncmoney.infra.user.persistence.entity;

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

  public static OrganizationEntity from(Organization organization) {
    OrganizationEntity entity = new OrganizationEntity();
    entity.setId(organization.getId());
    entity.setCreatedAt(organization.getCreatedAt());
    entity.setEmailOwner(organization.getEmailOwner());
    return entity;
  }

  public Organization toOrganization() {
    return Organization.restore(id, createdAt, emailOwner);
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
