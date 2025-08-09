package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UserAcceptedInvitationEvent implements DomainEvent {
  private final UUID invitedBy;
  private final OffsetDateTime acceptedAt;
  private final String emailInvited;
  private final UUID organizationId;
  private final OffsetDateTime dateTime;

  public UserAcceptedInvitationEvent(
      UUID invitedBy,
      OffsetDateTime acceptedAt,
      String emailInvited,
      UUID organizationId,
      OffsetDateTime dateTime) {
    this.invitedBy = invitedBy;
    this.acceptedAt = acceptedAt;
    this.emailInvited = emailInvited;
    this.organizationId = organizationId;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "UserAcceptedInvitationEvent{"
        + "invitedBy="
        + invitedBy
        + ", acceptedAt="
        + acceptedAt
        + ", emailInvited='"
        + emailInvited
        + '\''
        + ", organizationId="
        + organizationId
        + ", dateTime="
        + dateTime
        + '}';
  }

  public UUID getInvitedBy() {
    return invitedBy;
  }

  public OffsetDateTime getAcceptedAt() {
    return acceptedAt;
  }

  public String getEmailInvited() {
    return emailInvited;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
