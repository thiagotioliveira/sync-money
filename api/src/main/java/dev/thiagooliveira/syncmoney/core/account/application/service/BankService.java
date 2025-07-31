package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.util.List;
import java.util.UUID;

public interface BankService {

  Bank createBank(CreateBankInput input);

  List<Bank> getAll(UUID organizationId);
}
