package dev.thiagooliveira.syncmoney.infra.account.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.port.BankPort;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BankAdapter implements BankPort {

  private final BankRepository bankRepository;

  public BankAdapter(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Bank create(CreateBankInput input) {
    return this.bankRepository.save(BankEntity.from(input)).toBank();
  }

  @Override
  public boolean existsByName(UUID organizationId, String name) {
    return this.bankRepository.existsByOrganizationIdAndName(organizationId, name);
  }

  @Override
  public Optional<Bank> findById(UUID organizationId, UUID id) {
    return this.bankRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(BankEntity::toBank);
  }
}
