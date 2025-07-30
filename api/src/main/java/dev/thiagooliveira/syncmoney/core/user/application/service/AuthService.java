package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;

public interface AuthService {

  User login(String email, String password);

  User register(RegisterUserInput input);
}
