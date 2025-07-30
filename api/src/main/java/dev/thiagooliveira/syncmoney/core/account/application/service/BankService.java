package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;

public interface BankService {

  Bank createBank(CreateBankInput input);
}
