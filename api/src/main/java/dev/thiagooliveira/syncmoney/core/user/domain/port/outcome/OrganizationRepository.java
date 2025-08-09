package dev.thiagooliveira.syncmoney.core.user.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {

  Optional<Organization> findById(UUID id);

  Organization save(Organization input);
}
