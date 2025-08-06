package dev.thiagooliveira.syncmoney.infra.user.persistence.entity;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.InvitationStatus;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "invitations")
public class InvitationEntity {

  @Id private UUID id;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "invited_at")
  private OffsetDateTime invitedAt;

  @Column(name = "invited_by", nullable = false)
  private UUID invitedBy;

  @Column(nullable = false)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private InvitationStatus status;

  @Column(nullable = false)
  private UUID organizationId;

  public InvitationEntity() {}

  public static InvitationEntity create(InvitationInput input) {
    var entity = new InvitationEntity();
    entity.id = UUID.randomUUID();
    entity.createdAt = OffsetDateTime.now();
    entity.invitedBy = input.invitedBy();
    entity.email = input.email();
    entity.status = InvitationStatus.PENDING;
    entity.organizationId = input.organizationId();
    entity.invitedAt = null;
    return entity;
  }

  public static InvitationEntity restore(Invitation invitation) {
    var entity = new InvitationEntity();
    entity.id = invitation.getId();
    entity.createdAt = invitation.getCreatedAt();
    entity.invitedAt = invitation.getInvitedAt();
    entity.invitedBy = invitation.getInvitedBy();
    entity.email = invitation.getEmail();
    entity.status = invitation.getStatus();
    entity.organizationId = invitation.getOrganizationId();
    return entity;
  }

  public Invitation toInvitation() {
    return Invitation.restore(id, createdAt, invitedAt, invitedBy, email, status, organizationId);
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

  public OffsetDateTime getInvitedAt() {
    return invitedAt;
  }

  public void setInvitedAt(OffsetDateTime invitedAt) {
    this.invitedAt = invitedAt;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public InvitationStatus getStatus() {
    return status;
  }

  public void setStatus(InvitationStatus status) {
    this.status = status;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
