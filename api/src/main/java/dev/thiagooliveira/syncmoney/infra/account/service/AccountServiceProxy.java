package dev.thiagooliveira.syncmoney.infra.account.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceProxy implements AccountService {

  private final AccountService accountService;

  public AccountServiceProxy(AccountService accountService) {
    this.accountService = accountService;
  }

  @Transactional
  @Override
  public Bank createBank(CreateBankInput input) {
    return this.accountService.createBank(input);
  }

  @Transactional
  @Override
  public Account createAccount(CreateAccountInput input) {
    return this.accountService.createAccount(input);
  }

  @Override
  public Optional<AccountEnriched> getById(UUID organizationId, UUID accountId) {
    return this.accountService.getById(organizationId, accountId);
  }

  @Override
  public List<Account> getAll(UUID organizationId) {
    return this.accountService.getAll(organizationId);
  }
}
