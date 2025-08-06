package dev.thiagooliveira.syncmoney.infra.transaction.client;

import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountClientAdapter implements AccountClient {

  private final GetAccount getAccount;

  public AccountClientAdapter(GetAccount getAccount) {
    this.getAccount = getAccount;
  }

  @Override
  public boolean existsById(UUID organizationId, UUID id) {
    return this.getAccount.existsById(organizationId, id);
  }

  @Override
  public Optional<AccountEnriched> getById(UUID organizationId, UUID accountId) {
    return this.getAccount
        .getById(organizationId, accountId)
        .map(a -> new AccountEnriched(a.id(), a.name(), a.bank().getCurrency()));
  }
}
