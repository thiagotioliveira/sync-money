package dev.thiagooliveira.syncmoney.infra.user.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public boolean existByEmail(String email) {
    return this.userJpaRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public Optional<UserWithPassword> getByEmail(String email) {
    return this.userJpaRepository.findByEmailIgnoreCase(email).map(UserEntity::toUserWithPassword);
  }

  @Override
  public Optional<User> getById(UUID organizationId, UUID id) {
    return this.userJpaRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(UserEntity::toUser);
  }

  @Override
  public List<User> getAll(UUID organizationId) {
    return this.userJpaRepository.findAllByOrganizationId(organizationId).stream()
        .map(UserEntity::toUser)
        .toList();
  }

  @Override
  public User create(CreateUserInput input, Organization organization) {
    return this.userJpaRepository.save(UserEntity.create(input, organization)).toUserCreated();
  }
}
