package dev.thiagooliveira.syncmoney.infra.account.service;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.usecase.CreateAccount;
import dev.thiagooliveira.syncmoney.application.account.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final CreateBank createBank;
  private final CreateAccount createAccount;
  private final GetAccount getAccount;

  public AccountService(CreateBank createBank, CreateAccount createAccount, GetAccount getAccount) {
    this.createBank = createBank;
    this.createAccount = createAccount;
    this.getAccount = getAccount;
  }

  public Bank createBank(CreateBankInput input) {
    return this.createBank.execute(input);
  }

  @Transactional
  public Account createAccount(CreateAccountInput input) {
    return this.createAccount.execute(input);
  }

  public Optional<Account> getById(UUID organizationId, UUID accountId) {
    return this.getAccount.findById(organizationId, accountId);
  }

  public List<Account> findAll(UUID organizationId) {
    return this.getAccount.findAll(organizationId);
  }
}
