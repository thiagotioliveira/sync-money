package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.GetInvitations;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.GetUser;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.InviteUser;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

  private final EventPublisher eventPublisher;
  private final GetUser getUser;
  private final InviteUser inviteUser;
  private final GetInvitations getInvitations;

  public UserServiceImpl(
      EventPublisher eventPublisher,
      GetUser getUser,
      InviteUser inviteUser,
      GetInvitations getInvitations) {
    this.eventPublisher = eventPublisher;
    this.getUser = getUser;
    this.inviteUser = inviteUser;
    this.getInvitations = getInvitations;
  }

  @Override
  public List<User> getAll(UUID organizationId) {
    return this.getUser.all(organizationId);
  }

  @Override
  public Optional<User> getById(UUID organizationId, UUID userId) {
    return this.getUser.byId(organizationId, userId);
  }

  @Override
  public Invitation invite(InvitationInput input) {
    Invitation invitation = this.inviteUser.execute(input);
    invitation.getEvents().forEach(this.eventPublisher::publish);
    return invitation;
  }

  @Override
  public List<Invitation> getAllInvitations(UUID organizationId) {
    return this.getInvitations.execute(organizationId);
  }
}
