package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserLoggedEvent;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import java.time.OffsetDateTime;

public class Login {

  private final EventPublisher eventPublisher;
  private final CredentialEncoder credentialEncoder;
  private final UserRepository userRepository;

  public Login(
      EventPublisher eventPublisher,
      CredentialEncoder credentialEncoder,
      UserRepository userRepository) {
    this.eventPublisher = eventPublisher;
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
    user.registerEvent(new UserLoggedEvent(user.getId(), OffsetDateTime.now()));
    user.getEvents().forEach(this.eventPublisher::publish);
    return user.toUser();
  }
}
