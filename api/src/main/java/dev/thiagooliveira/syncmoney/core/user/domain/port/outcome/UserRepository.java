package dev.thiagooliveira.syncmoney.core.user.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  boolean existByEmail(String email);

  Optional<UserWithPassword> getByEmail(String email);

  Optional<User> getById(UUID organizationId, UUID id);

  List<User> getAll(UUID organizationId);

  User save(UserWithPassword user);
}
