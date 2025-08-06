package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AccountEnriched;
import java.util.Optional;
import java.util.UUID;

public interface AccountClient {

  boolean existsById(UUID organizationId, UUID id);

  Optional<AccountEnriched> getById(UUID organizationId, UUID accountId);
}
