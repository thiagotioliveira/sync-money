package dev.thiagooliveira.syncmoney.application.user.usecase;

import dev.thiagooliveira.syncmoney.application.user.domain.model.User;
import dev.thiagooliveira.syncmoney.application.user.domain.port.UserPort;
import java.util.Optional;
import java.util.UUID;

public class GetUser {

  public final UserPort userPort;

  public GetUser(UserPort userPort) {
    this.userPort = userPort;
  }

  public Optional<User> byId(UUID id) {
    return this.userPort.findById(id);
  }
}
