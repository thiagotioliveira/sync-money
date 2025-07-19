package dev.thiagooliveira.syncmoney.application.account.domain.port;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountPort {

  Account create(CreateAccountInput input);

  boolean existsByName(UUID organizationId, String name);

  Optional<Account> findById(UUID organizationId, UUID id);

  List<Account> findAll(UUID organizationId);
}
