package dev.thiagooliveira.syncmoney.infra.account.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankJpaRepository extends JpaRepository<BankEntity, UUID> {
  boolean existsByOrganizationIdAndName(UUID organizationId, String name);

  boolean existsByOrganizationIdAndId(UUID organizationId, UUID id);

  Optional<BankEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);

  List<BankEntity> findAllByOrganizationId(UUID organizationId);
}
