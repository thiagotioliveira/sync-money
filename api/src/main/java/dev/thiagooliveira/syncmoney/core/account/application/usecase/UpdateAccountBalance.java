package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;

public class UpdateAccountBalance {
  private final EventPublisher eventPublisher;
  private final AccountRepository accountRepository;

  public UpdateAccountBalance(EventPublisher eventPublisher, AccountRepository accountRepository) {
    this.eventPublisher = eventPublisher;
    this.accountRepository = accountRepository;
  }

  public void execute(UpdateAccountBalanceInput input) {
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
    account.getEvents().forEach(this.eventPublisher::publish);
  }
}
