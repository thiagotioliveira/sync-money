package dev.thiagooliveira.syncmoney.application.account.domain.port;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import java.util.Optional;
import java.util.UUID;

public interface BankPort {

  Bank create(CreateBankInput input);

  boolean existsByName(UUID organizationId, String name);

  Optional<Bank> findById(UUID organizationId, UUID id);
}
