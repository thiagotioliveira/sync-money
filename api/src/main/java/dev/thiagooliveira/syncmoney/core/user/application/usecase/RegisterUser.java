package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
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
                  this.invitationRepository.save(invitation.accepted());
                  return this.organizationRepository
                      .findById(invitation.getOrganizationId())
                      .orElseThrow();
                })
            .orElseGet(() -> this.organizationRepository.save(Organization.create(input.email())));

    return this.userRepository.save(UserWithPassword.create(organization, input)).registered();
  }
}
