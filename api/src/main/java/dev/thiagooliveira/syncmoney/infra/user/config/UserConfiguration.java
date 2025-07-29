package dev.thiagooliveira.syncmoney.infra.user.config;

import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserServiceImpl;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.CreateUser;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.GetUser;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.adapter.OrganizationRepositoryAdapter;
import dev.thiagooliveira.syncmoney.infra.user.persistence.adapter.UserRepositoryAdapter;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean
  public UserRepository userPort(UserJpaRepository userJpaRepository) {
    return new UserRepositoryAdapter(userJpaRepository);
  }

  @Bean
  public OrganizationRepository organizationPort(
      OrganizationJpaRepository organizationJpaRepository) {
    return new OrganizationRepositoryAdapter(organizationJpaRepository);
  }

  @Bean
  public CreateUser createUser(
      EventPublisher eventPublisher,
      OrganizationRepository organizationRepository,
      UserRepository userRepository) {
    return new CreateUser(eventPublisher, organizationRepository, userRepository);
  }

  @Bean
  public GetUser getUser(UserRepository userRepository) {
    return new GetUser(userRepository);
  }

  @Bean
  public UserService userService(CreateUser createUser, GetUser getUser) {
    return new UserServiceImpl(createUser, getUser);
  }
}
