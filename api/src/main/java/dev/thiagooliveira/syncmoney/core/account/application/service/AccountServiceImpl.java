package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateAccount;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.UpdateAccountBalance;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {

  private final DomainEventContext domainEventContext;
  private final CreateAccount createAccount;
  private final GetAccount getAccount;
  private final UpdateAccountBalance updateAccountBalance;

  public AccountServiceImpl(
      DomainEventContext domainEventContext,
      CreateAccount createAccount,
      GetAccount getAccount,
      UpdateAccountBalance updateAccountBalance) {
    this.domainEventContext = domainEventContext;
    this.createAccount = createAccount;
    this.getAccount = getAccount;
    this.updateAccountBalance = updateAccountBalance;
  }

  @Override
  public Account createAccount(CreateAccountInput input) {
    return this.domainEventContext.execute(() -> this.createAccount.execute(input));
  }

  @Override
  public Optional<AccountEnriched> getById(UUID organizationId, UUID accountId) {
    return this.getAccount.getById(organizationId, accountId);
  }

  @Override
  public List<Account> getAll(UUID organizationId) {
    return this.getAccount.getAll(organizationId);
  }

  @Override
  public Account updateBalance(UpdateAccountBalanceInput input) {
    return this.domainEventContext.execute(() -> this.updateAccountBalance.execute(input));
  }
}
