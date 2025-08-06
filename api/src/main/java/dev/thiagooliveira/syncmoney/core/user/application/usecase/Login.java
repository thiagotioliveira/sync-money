package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;

public class Login {

  private final CredentialEncoder credentialEncoder;
  private final UserRepository userRepository;

  public Login(CredentialEncoder credentialEncoder, UserRepository userRepository) {
    this.credentialEncoder = credentialEncoder;
    this.userRepository = userRepository;
  }

  public User execute(String email, String password) {
    var user =
        this.userRepository
            .getByEmail(email)
            .orElseThrow(() -> BusinessLogicException.notAuthorized("user not found"));
    if (!this.credentialEncoder.matches(password, user.getPassword())) {
      throw BusinessLogicException.notAuthorized("e-mail and/or password invalid");
    }
    return user.toUser().addUserLoggedEvent();
  }
}
