package dev.thiagooliveira.syncmoney.core.account.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

  Account create(CreateAccountInput input);

  boolean existsById(UUID organizationId, UUID id);

  boolean existsByName(UUID organizationId, String name);

  Optional<Account> getById(UUID organizationId, UUID id);

  List<Account> getAll(UUID organizationId);

  Account update(Account account);
}
