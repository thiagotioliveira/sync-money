package dev.thiagooliveira.syncmoney.infra.account.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class AccountServiceProxy implements AccountService {

  private final AccountService accountService;

  public AccountServiceProxy(AccountService accountService) {
    this.accountService = accountService;
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

  @Override
  public Account updateBalance(UpdateAccountBalanceInput input) {
    return this.accountService.updateBalance(input);
  }
}
