package dev.thiagooliveira.syncmoney.core.account.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankRepository {

  Bank create(CreateBankInput input);

  boolean existsByName(UUID organizationId, String name);

  boolean existsById(UUID organizationId, UUID id);

  Optional<Bank> getById(UUID organizationId, UUID id);

  List<Bank> getAll(UUID organizationId);
}
