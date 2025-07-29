package dev.thiagooliveira.syncmoney.core.account.domain.port.income;

import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.UpdateAccountBalance;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.event.TransactionPaidEvent;

public class TransactionEventListener {

  private final UpdateAccountBalance updateAccountBalance;

  public TransactionEventListener(UpdateAccountBalance updateAccountBalance) {
    this.updateAccountBalance = updateAccountBalance;
  }

  public void listen(TransactionPaidEvent event) {
    this.updateAccountBalance.execute(new UpdateAccountBalanceInput(event));
  }
}
