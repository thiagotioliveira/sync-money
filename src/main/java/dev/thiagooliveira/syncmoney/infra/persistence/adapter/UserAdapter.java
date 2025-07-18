package dev.thiagooliveira.syncmoney.infra.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.port.UserPort;
import dev.thiagooliveira.syncmoney.infra.persistence.entity.UserEntity;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

public class UserAdapter implements UserPort {

  private final UserRepository userRepository;

  public UserAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean existByEmail(String email) {
    return this.userRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return this.userRepository.findById(id).map(UserEntity::toUser);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return this.userRepository.findByEmailIgnoreCase(email).map(UserEntity::toUser);
  }

  @Override
  public User save(CreateUserInput input, Organization organization) {
    return this.userRepository.save(UserEntity.from(input, organization)).toUser();
  }

  @Override
  public User update(User user) {
    var userEntity =
        this.userRepository
            .findById(user.getId())
            .orElseThrow(() -> BusinessLogicException.notFound("user not found"));
    userEntity.update(user);
    return this.userRepository.save(userEntity).toUser();
  }
}
