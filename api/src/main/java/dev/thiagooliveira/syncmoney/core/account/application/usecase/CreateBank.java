package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;

public class CreateBank {

  private final BankRepository bankRepository;

  public CreateBank(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  public Bank execute(CreateBankInput input) {
    if (this.bankRepository.existsByName(input.organizationId(), input.name())) {
      throw BusinessLogicException.badRequest("bank already exists");
    }
    return bankRepository.create(input).created();
  }
}
