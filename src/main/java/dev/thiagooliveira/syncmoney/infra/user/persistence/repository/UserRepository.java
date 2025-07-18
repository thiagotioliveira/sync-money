package dev.thiagooliveira.syncmoney.infra.user.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  boolean existsByEmailIgnoreCase(String email);

  Optional<UserEntity> findByEmailIgnoreCase(String email);
}
