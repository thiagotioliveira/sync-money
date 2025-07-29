package dev.thiagooliveira.syncmoney.infra.user.service;

import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceProxy implements UserService {

  private final UserService userService;

  public UserServiceProxy(UserService userService) {
    this.userService = userService;
  }

  @Transactional
  @Override
  public User create(CreateUserInput input) {
    return this.userService.create(input);
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
  public Optional<UserWithPassword> getByEmail(String email) {
    return this.userService.getByEmail(email);
  }
}
