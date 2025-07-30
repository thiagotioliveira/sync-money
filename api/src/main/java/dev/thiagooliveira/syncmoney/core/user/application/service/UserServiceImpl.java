package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.user.application.usecase.GetUser;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

  private final GetUser getUser;

  public UserServiceImpl(GetUser getUser) {
    this.getUser = getUser;
  }

  @Override
  public List<User> getAll(UUID organizationId) {
    return this.getUser.all(organizationId);
  }

  @Override
  public Optional<User> getById(UUID organizationId, UUID userId) {
    return this.getUser.byId(organizationId, userId);
  }
}
