package dev.thiagooliveira.syncmoney.application.user.usecase;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.event.UserCreatedEvent;
import dev.thiagooliveira.syncmoney.application.user.port.UserPort;

public class CreateUser {

  private final EventPublisher eventPublisher;
  private final CreateOrganization createOrganization;
  private final UserPort userPort;

  public CreateUser(
      EventPublisher eventPublisher, CreateOrganization createOrganization, UserPort userPort) {
    this.eventPublisher = eventPublisher;
    this.createOrganization = createOrganization;
    this.userPort = userPort;
  }

  public User execute(CreateUserInput input) {
    if (this.userPort.existByEmail(input.email())) {
      throw new BusinessLogicException("email already exists");
    }
    var organization = this.createOrganization.execute(input.toCreateOrganizationInput());
    var user = this.userPort.save(input, organization);
    this.eventPublisher.publish(
        new UserCreatedEvent(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt()));
    return user;
  }
}
