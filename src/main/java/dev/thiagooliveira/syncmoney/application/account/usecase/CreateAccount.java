package dev.thiagooliveira.syncmoney.application.account.usecase;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.event.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;

public class CreateAccount {

  private final EventPublisher eventPublisher;
  private final GetBank getBank;
  private final AccountPort accountPort;

  public CreateAccount(EventPublisher eventPublisher, GetBank getBank, AccountPort accountPort) {
    this.eventPublisher = eventPublisher;
    this.getBank = getBank;
    this.accountPort = accountPort;
  }

  public Account execute(CreateAccountInput input) {
    if (this.accountPort.existsByName(input.organizationId(), input.name())) {
      throw BusinessLogicException.badRequest(
          "account with name '" + input.name() + "' already exists");
    }
    var bank =
        this.getBank
            .byId(input.organizationId(), input.bankId())
            .orElseThrow(
                () ->
                    BusinessLogicException.notFound(
                        "bank with id '" + input.bankId() + "' not found"));
    Account account = this.accountPort.create(input);
    this.eventPublisher.publish(new AccountCreatedEvent(account));
    return account;
  }
}
