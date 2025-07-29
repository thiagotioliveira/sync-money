package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import java.util.UUID;

public interface AccountClient {

  boolean existsById(UUID organizationId, UUID id);
}
