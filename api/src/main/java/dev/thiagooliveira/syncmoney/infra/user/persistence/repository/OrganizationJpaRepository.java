package dev.thiagooliveira.syncmoney.infra.user.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationJpaRepository extends JpaRepository<OrganizationEntity, UUID> {}
