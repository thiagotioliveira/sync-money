package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserInvitedEvent implements Event {

  private UUID invitedBy;
  private OffsetDateTime createdAt;
  private String emailInvited;
  private UUID organizationId;

  public UserInvitedEvent(
      UUID invitedBy, OffsetDateTime createdAt, String emailInvited, UUID organizationId) {
    this.invitedBy = invitedBy;
    this.createdAt = createdAt;
    this.emailInvited = emailInvited;
    this.organizationId = organizationId;
  }

  public UUID getInvitedBy() {
    return invitedBy;
  }

  public String getEmailInvited() {
    return emailInvited;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "UserInvitedEvent{"
        + "invitedBy="
        + invitedBy
        + ", createdAt="
        + createdAt
        + ", emailInvited='"
        + emailInvited
        + '\''
        + ", organizationId="
        + organizationId
        + '}';
  }
}
