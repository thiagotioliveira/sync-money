package dev.thiagooliveira.syncmoney.infra.user.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.service.AuthService;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class AuthServiceProxy implements AuthService {

  private final AuthService authService;

  public AuthServiceProxy(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public User login(String email, String password) {
    return this.authService.login(email, password);
  }

  @Transactional
  @Override
  public User register(RegisterUserInput input) {
    return this.authService.register(input);
  }
}
