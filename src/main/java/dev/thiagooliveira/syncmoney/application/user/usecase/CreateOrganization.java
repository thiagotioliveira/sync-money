package dev.thiagooliveira.syncmoney.application.user.usecase;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.application.user.event.OrganizationCreatedEvent;
import dev.thiagooliveira.syncmoney.application.user.port.OrganizationPort;

public class CreateOrganization {

  private final EventPublisher eventPublisher;
  private final OrganizationPort organizationPort;

  public CreateOrganization(EventPublisher eventPublisher, OrganizationPort organizationPort) {
    this.eventPublisher = eventPublisher;
    this.organizationPort = organizationPort;
  }

  public Organization execute(CreateOrganizationInput input) {
    var organization = this.organizationPort.save(input);
    this.eventPublisher.publish(
        new OrganizationCreatedEvent(
            organization.id(), organization.createdAt(), organization.emailOwner()));
    return organization;
  }
}
