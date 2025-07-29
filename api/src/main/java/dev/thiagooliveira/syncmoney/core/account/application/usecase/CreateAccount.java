package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;

public class CreateAccount {

  private final EventPublisher eventPublisher;
  private final BankRepository bankRepository;
  private final AccountRepository accountRepository;

  public CreateAccount(
      EventPublisher eventPublisher,
      BankRepository bankRepository,
      AccountRepository accountRepository) {
    this.eventPublisher = eventPublisher;
    this.bankRepository = bankRepository;
    this.accountRepository = accountRepository;
  }

  public Account execute(CreateAccountInput input) {
    if (this.accountRepository.existsByName(input.organizationId(), input.name())) {
      throw BusinessLogicException.badRequest(
          "account with name '" + input.name() + "' already exists");
    }
    if (!this.bankRepository.existsById(input.organizationId(), input.bankId())) {
      throw BusinessLogicException.notFound("bank with id '" + input.bankId() + "' not found");
    }
    Account account = this.accountRepository.create(input);
    account.getEvents().forEach(this.eventPublisher::publish);
    return account;
  }
}
