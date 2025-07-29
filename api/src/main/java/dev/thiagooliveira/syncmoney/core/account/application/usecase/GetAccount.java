package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetAccount {

  private final AccountRepository accountRepository;
  private final BankRepository bankRepository;

  public GetAccount(AccountRepository accountRepository, BankRepository bankRepository) {
    this.accountRepository = accountRepository;
    this.bankRepository = bankRepository;
  }

  public boolean existsById(UUID organizationId, UUID id) {
    return this.accountRepository.existsById(organizationId, id);
  }

  public Optional<AccountEnriched> getById(UUID organizationId, UUID id) {
    return this.accountRepository
        .getById(organizationId, id)
        .map(
            a -> {
              var bank = this.bankRepository.getById(organizationId, a.getBankId()).orElseThrow();
              return new AccountEnriched(a, bank);
            });
  }

  public List<Account> getAll(UUID organizationId) {
    return this.accountRepository.getAll(organizationId);
  }
}
