package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.Login;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.RegisterUser;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;

public class AuthServiceImpl implements AuthService {

  private final EventPublisher eventPublisher;
  private final Login login;
  private final RegisterUser registerUser;

  public AuthServiceImpl(EventPublisher eventPublisher, Login login, RegisterUser registerUser) {
    this.eventPublisher = eventPublisher;
    this.login = login;
    this.registerUser = registerUser;
  }

  @Override
  public User login(String email, String password) {
    User user = this.login.execute(email, password);
    user.getEvents().forEach(this.eventPublisher::publish);
    return user;
  }

  @Override
  public User register(RegisterUserInput input) {
    User user = this.registerUser.execute(input);
    user.getEvents().forEach(this.eventPublisher::publish);
    return user;
  }
}
