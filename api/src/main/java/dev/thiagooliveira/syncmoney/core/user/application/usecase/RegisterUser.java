package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;

public class RegisterUser {

  private final OrganizationRepository organizationRepository;
  private final UserRepository userRepository;
  private final InvitationRepository invitationRepository;

  public RegisterUser(
      OrganizationRepository organizationRepository,
      UserRepository userRepository,
      InvitationRepository invitationRepository) {
    this.organizationRepository = organizationRepository;
    this.userRepository = userRepository;
    this.invitationRepository = invitationRepository;
  }

  public User execute(RegisterUserInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("email already exists");
    }
    var organization =
        this.invitationRepository
            .getByEmail(input.email())
            .map(
                invitation -> {
                  this.invitationRepository.update(invitation.invited());
                  return this.organizationRepository
                      .findById(invitation.getOrganizationId())
                      .orElseThrow();
                })
            .orElseGet(() -> this.organizationRepository.create(input.toCreateOrganizationInput()));
    return this.userRepository.register(input, organization).addUserRegisteredEvent();
  }

  @Deprecated
  public User execute(CreateUserInOrganizationInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("email already exists");
    }
    var organization =
        this.organizationRepository
            .findById(input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("organization not found"));
    return this.userRepository
        .register(
            new RegisterUserInput(input.email(), input.name(), input.password()), organization)
        .addUserRegisteredEvent();
  }
}
