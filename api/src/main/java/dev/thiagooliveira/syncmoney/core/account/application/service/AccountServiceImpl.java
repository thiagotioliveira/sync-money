package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateAccount;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventScoped;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
  private final EventPublisher eventPublisher;
  private final CreateAccount createAccount;
  private final GetAccount getAccount;

  public AccountServiceImpl(
      EventPublisher eventPublisher, CreateAccount createAccount, GetAccount getAccount) {
    this.eventPublisher = eventPublisher;
    this.createAccount = createAccount;
    this.getAccount = getAccount;
  }

  @DomainEventScoped
  @Override
  public Account createAccount(CreateAccountInput input) {
    Account account = this.createAccount.execute(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return account;
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
