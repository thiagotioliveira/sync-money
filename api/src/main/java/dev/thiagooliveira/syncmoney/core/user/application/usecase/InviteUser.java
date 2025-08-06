package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;

public class InviteUser {

  private final UserRepository userRepository;
  private final InvitationRepository invitationRepository;

  public InviteUser(UserRepository userRepository, InvitationRepository invitationRepository) {
    this.userRepository = userRepository;
    this.invitationRepository = invitationRepository;
  }

  public Invitation execute(InvitationInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("user already registered");
    }
    return this.invitationRepository.invite(input).addInvitationInvitedEvent();
  }
}
