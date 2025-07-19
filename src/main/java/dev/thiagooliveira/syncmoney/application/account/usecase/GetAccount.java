package dev.thiagooliveira.syncmoney.application.account.usecase;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetAccount {

  private final AccountPort accountPort;

  public GetAccount(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  public Optional<Account> findById(UUID organizationId, UUID id) {
    return this.accountPort.findById(organizationId, id);
  }

  public List<Account> findAll(UUID organizationId) {
    return this.accountPort.findAll(organizationId);
  }
}
