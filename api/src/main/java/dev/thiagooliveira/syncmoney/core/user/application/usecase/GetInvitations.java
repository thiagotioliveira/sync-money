package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import java.util.List;
import java.util.UUID;

public class GetInvitations {

  private final InvitationRepository invitationRepository;

  public GetInvitations(InvitationRepository invitationRepository) {
    this.invitationRepository = invitationRepository;
  }

  public List<Invitation> execute(UUID organizationId) {
    return this.invitationRepository.getAll(organizationId);
  }
}
