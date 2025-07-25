package dev.thiagooliveira.syncmoney.application.user.usecase;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.domain.dto.event.UserCreatedEvent;
import dev.thiagooliveira.syncmoney.application.user.domain.model.User;
import dev.thiagooliveira.syncmoney.application.user.domain.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.application.user.domain.port.UserPort;

public class CreateUser {

  private final EventPublisher eventPublisher;
  private final OrganizationPort organizationPort;
  private final UserPort userPort;

  public CreateUser(
      EventPublisher eventPublisher, OrganizationPort organizationPort, UserPort userPort) {
    this.eventPublisher = eventPublisher;
    this.organizationPort = organizationPort;
    this.userPort = userPort;
  }

  public User execute(CreateUserInput input) {
    if (this.userPort.existByEmail(input.email())) {
      throw BusinessLogicException.badRequest("email already exists");
    }
    var organization = this.organizationPort.save(input.toCreateOrganizationInput());
    var user = this.userPort.save(input, organization);
    this.eventPublisher.publish(new UserCreatedEvent(user));
    return user;
  }
}
