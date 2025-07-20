package dev.thiagooliveira.syncmoney.infra.user.config;

import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.user.domain.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.application.user.domain.port.UserPort;
import dev.thiagooliveira.syncmoney.application.user.usecase.CreateUser;
import dev.thiagooliveira.syncmoney.application.user.usecase.GetUser;
import dev.thiagooliveira.syncmoney.infra.user.persistence.adapter.OrganizationAdapter;
import dev.thiagooliveira.syncmoney.infra.user.persistence.adapter.UserAdapter;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean
  public UserPort userPort(UserRepository userRepository) {
    return new UserAdapter(userRepository);
  }

  @Bean
  public OrganizationPort organizationPort(OrganizationRepository organizationRepository) {
    return new OrganizationAdapter(organizationRepository);
  }

  @Bean
  public CreateUser createUser(
      EventPublisher eventPublisher, OrganizationPort organizationPort, UserPort userPort) {
    return new CreateUser(eventPublisher, organizationPort, userPort);
  }

  @Bean
  public GetUser getUser(UserPort userPort) {
    return new GetUser(userPort);
  }
}
