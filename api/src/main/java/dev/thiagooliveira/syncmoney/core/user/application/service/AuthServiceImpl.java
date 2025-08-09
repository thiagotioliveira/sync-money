package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.Login;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.RegisterUser;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;

public class AuthServiceImpl implements AuthService {

  private final DomainEventContext domainEventContext;
  private final Login login;
  private final RegisterUser registerUser;

  public AuthServiceImpl(
      DomainEventContext domainEventContext, Login login, RegisterUser registerUser) {
    this.domainEventContext = domainEventContext;
    this.login = login;
    this.registerUser = registerUser;
  }

  @Override
  public User login(String email, String password) {
    return this.domainEventContext.execute(() -> this.login.execute(email, password));
  }

  @Override
  public User register(RegisterUserInput input) {
    return this.domainEventContext.execute(() -> this.registerUser.execute(input));
  }
}
