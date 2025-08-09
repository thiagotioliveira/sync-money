package dev.thiagooliveira.syncmoney.core.user.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository {
  Invitation invite(InvitationInput input);

  Invitation save(Invitation invitation);

  Optional<Invitation> getByEmail(String email);

  List<Invitation> getAll(UUID organizationId);
}
