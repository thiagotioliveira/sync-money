package dev.thiagooliveira.syncmoney.infra.user.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
  boolean existsByEmailIgnoreCase(String email);

  Optional<UserEntity> findByEmailIgnoreCase(String email);

  List<UserEntity> findAllByOrganizationId(UUID organizationId);

  Optional<UserEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);
}
