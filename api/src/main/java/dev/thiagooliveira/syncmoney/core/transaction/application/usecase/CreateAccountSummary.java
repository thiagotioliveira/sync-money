package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateFirstAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;

public class CreateAccountSummary {

  private final AccountSummaryRepository accountSummaryRepository;

  public CreateAccountSummary(AccountSummaryRepository accountSummaryRepository) {
    this.accountSummaryRepository = accountSummaryRepository;
  }

  public AccountSummary execute(CreateFirstAccountSummaryInput input) {
    return this.accountSummaryRepository.save(
        AccountSummary.create(input.accountId(), input.yearMonth()));
  }

  public AccountSummary execute(CreateAccountSummaryInput input) {
    return this.accountSummaryRepository.save(AccountSummary.create(input));
  }
}
