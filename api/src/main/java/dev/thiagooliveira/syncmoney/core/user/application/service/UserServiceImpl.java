package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
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

  private final DomainEventContext domainEventContext;
  private final GetUser getUser;
  private final InviteUser inviteUser;
  private final GetInvitations getInvitations;

  public UserServiceImpl(
      DomainEventContext domainEventContext,
      GetUser getUser,
      InviteUser inviteUser,
      GetInvitations getInvitations) {
    this.domainEventContext = domainEventContext;
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
    return this.domainEventContext.execute(() -> this.inviteUser.execute(input));
  }

  @Override
  public List<Invitation> getAllInvitations(UUID organizationId) {
    return this.getInvitations.execute(organizationId);
  }
}
