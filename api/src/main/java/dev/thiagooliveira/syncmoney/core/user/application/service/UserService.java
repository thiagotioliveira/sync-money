package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  User create(CreateUserInput input);

  List<User> getAll(UUID organizationId);

  Optional<User> getById(UUID organizationId, UUID userId);

  Optional<UserWithPassword> getByEmail(String email);
}
