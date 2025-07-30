package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;

public class RegisterUser {

  private final EventPublisher eventPublisher;
  private final OrganizationRepository organizationRepository;
  private final UserRepository userRepository;

  public RegisterUser(
      EventPublisher eventPublisher,
      OrganizationRepository organizationRepository,
      UserRepository userRepository) {
    this.eventPublisher = eventPublisher;
    this.organizationRepository = organizationRepository;
    this.userRepository = userRepository;
  }

  public User execute(RegisterUserInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("email already exists");
    }
    var organization = this.organizationRepository.create(input.toCreateOrganizationInput());
    var user = this.userRepository.register(input, organization);
    publishEvents(user);
    return user;
  }

  public User execute(CreateUserInOrganizationInput input) {
    if (this.userRepository.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("email already exists");
    }
    var organization =
        this.organizationRepository
            .findById(input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("organization not found"));
    var user =
        this.userRepository.register(
            new RegisterUserInput(input.email(), input.name(), input.password()), organization);
    publishEvents(user);
    return user;
  }

  private void publishEvents(User user) {
    user.getEvents().forEach(this.eventPublisher::publish);
  }
}
