package dev.thiagooliveira.syncmoney.core.user.application.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.CreateUser;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.GetUser;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

  private final CreateUser createUser;
  private final GetUser getUser;

  public UserServiceImpl(CreateUser createUser, GetUser getUser) {
    this.createUser = createUser;
    this.getUser = getUser;
  }

  @Override
  public User create(CreateUserInput input) {
    return this.createUser.execute(input);
  }

  @Override
  public List<User> getAll(UUID organizationId) {
    return this.getUser.all(organizationId);
  }

  @Override
  public Optional<User> getById(UUID organizationId, UUID userId) {
    return this.getUser.byId(organizationId, userId);
  }

  @Override
  public Optional<UserWithPassword> getByEmail(String email) {
    return this.getUser.getByEmail(email);
  }
}
