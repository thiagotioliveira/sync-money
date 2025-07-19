package dev.thiagooliveira.syncmoney.infra.account.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, UUID> {
  boolean existsByOrganizationIdAndName(UUID organizationId, String name);

  Optional<BankEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);
}
