package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;

public class UpdateAccountBalance {
  private final AccountRepository accountRepository;

  public UpdateAccountBalance(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account execute(UpdateAccountBalanceInput input) {
    var account =
        this.accountRepository
            .getById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    if (input.categoryType().isCredit()) {
      account = this.accountRepository.update(account.deposit(input.amount()));
    } else if (input.categoryType().isDebit()) {
      account = this.accountRepository.update(account.withdraw(input.amount()));
    } else {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    return account.addAccountUpdatedEvent();
  }
}
