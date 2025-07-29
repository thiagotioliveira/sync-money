package dev.thiagooliveira.syncmoney.infra.account.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.AccountEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
  boolean existsByOrganizationIdAndId(UUID organizationId, UUID id);

  boolean existsByOrganizationIdAndNameIgnoreCase(UUID organizationId, String name);

  Optional<AccountEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);

  List<AccountEntity> findAllByOrganizationId(UUID organizationId);
}
