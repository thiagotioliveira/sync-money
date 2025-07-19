package dev.thiagooliveira.syncmoney.application.account.usecase;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.port.BankPort;
import java.util.Optional;
import java.util.UUID;

public class GetBank {

  private final BankPort bankPort;

  public GetBank(BankPort bankPort) {
    this.bankPort = bankPort;
  }

  public Optional<Bank> byId(UUID organizationId, UUID id) {
    return this.bankPort.findById(organizationId, id);
  }
}
