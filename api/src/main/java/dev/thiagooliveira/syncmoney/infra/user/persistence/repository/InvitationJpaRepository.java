package dev.thiagooliveira.syncmoney.infra.user.persistence.repository;

import dev.thiagooliveira.syncmoney.core.user.domain.model.InvitationStatus;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.InvitationEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationJpaRepository extends JpaRepository<InvitationEntity, UUID> {

  Optional<InvitationEntity> findByEmailAndStatus(String email, InvitationStatus status);

  List<InvitationEntity> findAllByOrganizationId(UUID organizationId);
}
