package dev.thiagooliveira.syncmoney.infra.user.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.InvitationEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationJpaRepository extends JpaRepository<InvitationEntity, UUID> {

  Optional<InvitationEntity> findByEmail(String email);

  List<InvitationEntity> findAllByOrganizationId(UUID organizationId);
}
