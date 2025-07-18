package dev.thiagooliveira.syncmoney.application.user.port;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
import java.util.Optional;
import java.util.UUID;

public interface UserPort {

  boolean existByEmail(String email);

  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

  User save(CreateUserInput input, Organization organization);

  User update(User user);
}
