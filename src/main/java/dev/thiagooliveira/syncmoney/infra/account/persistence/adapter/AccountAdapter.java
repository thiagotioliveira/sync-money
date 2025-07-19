package dev.thiagooliveira.syncmoney.infra.account.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.AccountEntity;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter implements AccountPort {

  private final AccountRepository accountRepository;

  public AccountAdapter(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account create(CreateAccountInput input) {
    return this.accountRepository.save(AccountEntity.from(input)).toAccount();
  }

  @Override
  public boolean existsByName(UUID organizationId, String name) {
    return this.accountRepository.existsByOrganizationIdAndNameIgnoreCase(organizationId, name);
  }

  @Override
  public Optional<Account> findById(UUID organizationId, UUID id) {
    return this.accountRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(AccountEntity::toAccount);
  }

  @Override
  public List<Account> findAll(UUID organizationId) {
    return this.accountRepository.findAllByOrganizationId(organizationId).stream()
        .map(AccountEntity::toAccount)
        .toList();
  }
}
