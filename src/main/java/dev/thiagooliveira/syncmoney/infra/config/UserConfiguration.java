package dev.thiagooliveira.syncmoney.infra.config;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.user.port.UserPort;
import dev.thiagooliveira.syncmoney.application.user.usecase.CreateOrganization;
import dev.thiagooliveira.syncmoney.application.user.usecase.CreateUser;
import dev.thiagooliveira.syncmoney.application.user.usecase.GetUser;
import dev.thiagooliveira.syncmoney.infra.persistence.adapter.UserAdapter;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean
  public UserPort userPort(UserRepository userRepository) {
    return new UserAdapter(userRepository);
  }

  @Bean
  public CreateUser createUser(
      EventPublisher eventPublisher, CreateOrganization createOrganization, UserPort userPort) {
    return new CreateUser(eventPublisher, createOrganization, userPort);
  }

  @Bean
  public GetUser getUser(UserPort userPort) {
    return new GetUser(userPort);
  }
}
