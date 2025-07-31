package dev.thiagooliveira.syncmoney.infra.user.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.InvitationInput;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceProxy implements UserService {

  private final UserService userService;

  public UserServiceProxy(UserService userService) {
    this.userService = userService;
  }

  @Override
  public List<User> getAll(UUID organizationId) {
    return this.userService.getAll(organizationId);
  }

  @Override
  public Optional<User> getById(UUID organizationId, UUID userId) {
    return this.userService.getById(organizationId, userId);
  }

  @Override
  public Invitation invite(InvitationInput input) {
    return this.userService.invite(input);
  }

  @Override
  public List<Invitation> getAllInvitations(UUID organizationId) {
    return this.userService.getAllInvitations(organizationId);
  }
}
