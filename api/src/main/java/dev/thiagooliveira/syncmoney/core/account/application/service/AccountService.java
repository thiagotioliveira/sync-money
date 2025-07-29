package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

  Bank createBank(CreateBankInput input);

  Account createAccount(CreateAccountInput input);

  Optional<AccountEnriched> getById(UUID organizationId, UUID accountId);

  List<Account> getAll(UUID organizationId);
}
