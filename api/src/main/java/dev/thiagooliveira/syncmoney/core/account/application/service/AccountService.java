package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

  Account createAccount(CreateAccountInput input);

  Account updateBalance(UpdateAccountBalanceInput input);

  Optional<AccountEnriched> getById(UUID organizationId, UUID accountId);

  List<Account> getAll(UUID organizationId);
}
