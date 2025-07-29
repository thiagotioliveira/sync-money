package dev.thiagooliveira.syncmoney.infra.account.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.AccountEntity;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

  private final AccountJpaRepository accountJpaRepository;

  public AccountRepositoryAdapter(AccountJpaRepository accountJpaRepository) {
    this.accountJpaRepository = accountJpaRepository;
  }

  @Override
  public Account create(CreateAccountInput input) {
    return this.accountJpaRepository
        .save(AccountEntity.create(input))
        .toAccountCreated(input.userId(), input.initialBalance());
  }

  @Override
  public boolean existsById(UUID organizationId, UUID id) {
    return this.accountJpaRepository.existsByOrganizationIdAndId(organizationId, id);
  }

  @Override
  public boolean existsByName(UUID organizationId, String name) {
    return this.accountJpaRepository.existsByOrganizationIdAndNameIgnoreCase(organizationId, name);
  }

  @Override
  public Optional<Account> getById(UUID organizationId, UUID id) {
    return this.accountJpaRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(AccountEntity::toAccount);
  }

  @Override
  public List<Account> getAll(UUID organizationId) {
    return this.accountJpaRepository.findAllByOrganizationId(organizationId).stream()
        .map(AccountEntity::toAccount)
        .toList();
  }

  @Override
  public Account update(Account account) {
    return this.accountJpaRepository.save(AccountEntity.restore(account)).toAccountUpdated();
  }
}
