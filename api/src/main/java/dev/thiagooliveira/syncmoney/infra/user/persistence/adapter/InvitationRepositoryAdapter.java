package dev.thiagooliveira.syncmoney.infra.user.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.InvitationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.InvitationJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class InvitationRepositoryAdapter implements InvitationRepository {

  private final InvitationJpaRepository invitationJpaRepository;

  public InvitationRepositoryAdapter(InvitationJpaRepository invitationJpaRepository) {
    this.invitationJpaRepository = invitationJpaRepository;
  }

  @Override
  public Invitation invite(InvitationInput input) {
    return this.invitationJpaRepository.save(InvitationEntity.create(input)).toInvitationInvited();
  }

  @Override
  public Invitation update(Invitation invitation) {
    return this.invitationJpaRepository.save(InvitationEntity.restore(invitation)).toInvitation();
  }

  @Override
  public Optional<Invitation> getByEmail(String email) {
    return this.invitationJpaRepository.findByEmail(email).map(InvitationEntity::toInvitation);
  }

  @Override
  public List<Invitation> getAll(UUID organizationId) {
    return this.invitationJpaRepository.findAllByOrganizationId(organizationId).stream()
        .map(InvitationEntity::toInvitation)
        .toList();
  }
}
