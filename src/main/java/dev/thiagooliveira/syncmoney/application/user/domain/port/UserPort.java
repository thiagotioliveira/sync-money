package dev.thiagooliveira.syncmoney.application.user.domain.port;

import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface UserPort {

  boolean existByEmail(String email);

  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

  User save(CreateUserInput input, Organization organization);

  User update(User user);
}
