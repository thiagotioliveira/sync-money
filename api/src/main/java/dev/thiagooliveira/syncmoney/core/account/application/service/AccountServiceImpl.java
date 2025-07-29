package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateAccount;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
  private final CreateBank createBank;
  private final CreateAccount createAccount;
  private final GetAccount getAccount;

  public AccountServiceImpl(
      CreateBank createBank, CreateAccount createAccount, GetAccount getAccount) {
    this.createBank = createBank;
    this.createAccount = createAccount;
    this.getAccount = getAccount;
  }

  @Override
  public Bank createBank(CreateBankInput input) {
    return this.createBank.execute(input);
  }

  @Override
  public Account createAccount(CreateAccountInput input) {
    return this.createAccount.execute(input);
  }

  @Override
  public Optional<AccountEnriched> getById(UUID organizationId, UUID accountId) {
    return this.getAccount.getById(organizationId, accountId);
  }

  @Override
  public List<Account> getAll(UUID organizationId) {
    return this.getAccount.getAll(organizationId);
  }
}
