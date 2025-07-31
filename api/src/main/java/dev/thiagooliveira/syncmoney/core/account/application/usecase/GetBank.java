package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import java.util.List;
import java.util.UUID;

public class GetBank {

  private final BankRepository bankRepository;

  public GetBank(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  public List<Bank> getAll(UUID organizationId) {
    return this.bankRepository.getAll(organizationId);
  }
}
