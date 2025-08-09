package dev.thiagooliveira.syncmoney.core.account.domain.port.income;

import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.UpdateAccountBalance;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionPaidEvent;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;

public class TransactionEventListener {

  private final EventPublisher eventPublisher;
  private final UpdateAccountBalance updateAccountBalance;

  public TransactionEventListener(
      EventPublisher eventPublisher, UpdateAccountBalance updateAccountBalance) {
    this.eventPublisher = eventPublisher;
    this.updateAccountBalance = updateAccountBalance;
  }

  public void on(TransactionPaidEvent event) {
    this.updateAccountBalance.execute(new UpdateAccountBalanceInput(event));
    DomainEventPublisher.publish(eventPublisher::publish);
  }
}
