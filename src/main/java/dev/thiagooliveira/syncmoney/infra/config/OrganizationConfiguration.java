package dev.thiagooliveira.syncmoney.infra.config;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.user.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.application.user.usecase.CreateOrganization;
import dev.thiagooliveira.syncmoney.infra.persistence.adapter.OrganizationAdapter;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.OrganizationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationConfiguration {

  @Bean
  public OrganizationPort organizationPort(OrganizationRepository organizationRepository) {
    return new OrganizationAdapter(organizationRepository);
  }

  @Bean
  public CreateOrganization createOrganization(
      EventPublisher eventPublisher, OrganizationPort organizationPort) {
    return new CreateOrganization(eventPublisher, organizationPort);
  }
}
