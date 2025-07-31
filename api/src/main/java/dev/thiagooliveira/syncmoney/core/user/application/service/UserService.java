package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  List<User> getAll(UUID organizationId);

  Optional<User> getById(UUID organizationId, UUID userId);

  Invitation invite(InvitationInput input);

  List<Invitation> getAllInvitations(UUID organizationId);
}
