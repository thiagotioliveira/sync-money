package dev.thiagooliveira.syncmoney.infra.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.persistence.entity.OrganizationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {}
