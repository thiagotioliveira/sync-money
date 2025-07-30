package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;

public class BankServiceImpl implements BankService {

  private final CreateBank createBank;

  public BankServiceImpl(CreateBank createBank) {
    this.createBank = createBank;
  }

  @Override
  public Bank createBank(CreateBankInput input) {
    return this.createBank.execute(input);
  }
}
