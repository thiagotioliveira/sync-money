package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;

public class InviteUser {

  private final EventPublisher eventPublisher;
  private final UserRepository userRepository;
  private final InvitationRepository invitationRepository;

  public InviteUser(
      EventPublisher eventPublisher,
      UserRepository userRepository,
      InvitationRepository invitationRepository) {
    this.eventPublisher = eventPublisher;
    this.userRepository = userRepository;
    this.invitationRepository = invitationRepository;
  }

  public Invitation execute(InvitationInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("user already registered");
    }
    var invitation = this.invitationRepository.invite(input);
    invitation.getEvents().forEach(this.eventPublisher::publish);
    return invitation;
  }
}
