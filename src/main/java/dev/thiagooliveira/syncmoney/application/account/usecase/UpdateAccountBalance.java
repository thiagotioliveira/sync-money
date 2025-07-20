package dev.thiagooliveira.syncmoney.application.account.usecase;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.event.AccountBalanceUpdatedEvent;
import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;

public class UpdateAccountBalance {
  private final EventPublisher eventPublisher;
  private final GetAccount getAccount;
  private final AccountPort accountPort;

  public UpdateAccountBalance(
      EventPublisher eventPublisher, GetAccount getAccount, AccountPort accountPort) {
    this.eventPublisher = eventPublisher;
    this.getAccount = getAccount;
    this.accountPort = accountPort;
  }

  public void execute(UpdateAccountBalanceInput input) {
    var account =
        this.getAccount
            .findById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    if (input.categoryType().isCredit()) {
      this.accountPort.update(account.deposit(input.amount()));
    } else if (input.categoryType().isDebit()) {
      this.accountPort.update(account.withdraw(input.amount()));
    } else {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    this.eventPublisher.publish(new AccountBalanceUpdatedEvent(account));
  }
}
