package dev.thiagooliveira.syncmoney.infra.user.config;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.service.AuthService;
import dev.thiagooliveira.syncmoney.core.user.application.service.AuthServiceImpl;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.Login;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.RegisterUser;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

  @Bean
  public Login login(
      EventPublisher eventPublisher,
      CredentialEncoder credentialEncoder,
      UserRepository userRepository) {
    return new Login(eventPublisher, credentialEncoder, userRepository);
  }

  @Bean
  public RegisterUser registerUser(
      EventPublisher eventPublisher,
      OrganizationRepository organizationRepository,
      UserRepository userRepository) {
    return new RegisterUser(eventPublisher, organizationRepository, userRepository);
  }

  @Bean
  public AuthService authService(Login login, RegisterUser registerUser) {
    return new AuthServiceImpl(login, registerUser);
  }
}
