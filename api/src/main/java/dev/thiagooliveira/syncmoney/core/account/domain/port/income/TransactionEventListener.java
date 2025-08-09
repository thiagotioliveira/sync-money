package dev.thiagooliveira.syncmoney.core.account.domain.port.income;

import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionPaidEvent;

public class TransactionEventListener {

  private final AccountService accountService;

  public TransactionEventListener(AccountService accountService) {
    this.accountService = accountService;
  }

  public void on(TransactionPaidEvent event) {
    this.accountService.updateBalance(new UpdateAccountBalanceInput(event));
  }
}
