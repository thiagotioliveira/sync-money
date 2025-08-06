package dev.thiagooliveira.syncmoney.core.user.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserInvitedEvent;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Invitation extends AggregateRoot {

  private UUID id;
  private OffsetDateTime createdAt;
  private OffsetDateTime invitedAt;
  private UUID invitedBy;
  private String email;
  private InvitationStatus status;
  private UUID organizationId;

  public static Invitation restore(
      UUID id,
      OffsetDateTime createdAt,
      OffsetDateTime invitedAt,
      UUID invitedBy,
      String email,
      InvitationStatus status,
      UUID organizationId) {
    var invitation = new Invitation();
    invitation.id = id;
    invitation.createdAt = createdAt;
    invitation.invitedAt = invitedAt;
    invitation.invitedBy = invitedBy;
    invitation.email = email;
    invitation.status = status;
    invitation.organizationId = organizationId;
    return invitation;
  }

  public Invitation invited() {
    if (this.status != InvitationStatus.PENDING) {
      throw BusinessLogicException.badRequest("this invitation has already been invited.");
    }
    this.invitedAt = OffsetDateTime.now();
    this.status = InvitationStatus.INVITED;
    return this;
  }

  public Invitation addInvitationInvitedEvent() {
    this.registerEvent(
        new UserInvitedEvent(this.invitedBy, this.createdAt, this.email, this.organizationId));
    return this;
  }

  public UUID getId() {
    return id;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public OffsetDateTime getInvitedAt() {
    return invitedAt;
  }

  public UUID getInvitedBy() {
    return invitedBy;
  }

  public String getEmail() {
    return email;
  }

  public InvitationStatus getStatus() {
    return status;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }
}
