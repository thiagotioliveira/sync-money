package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetUser {

  public final UserRepository userRepository;

  public GetUser(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> all(UUID organizationId) {
    return this.userRepository.getAll(organizationId);
  }

  public Optional<User> byId(UUID organizationId, UUID id) {
    return this.userRepository.getById(organizationId, id);
  }

  public Optional<UserWithPassword> getByEmail(String email) {
    return this.userRepository.getByEmail(email);
  }
}
