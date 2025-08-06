package dev.thiagooliveira.syncmoney.infra.account.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BankRepositoryAdapter implements BankRepository {

  private final BankJpaRepository bankJpaRepository;

  public BankRepositoryAdapter(BankJpaRepository bankJpaRepository) {
    this.bankJpaRepository = bankJpaRepository;
  }

  @Override
  public Bank create(CreateBankInput input) {
    return this.bankJpaRepository.save(BankEntity.create(input)).toBank();
  }

  @Override
  public boolean existsByName(UUID organizationId, String name) {
    return this.bankJpaRepository.existsByOrganizationIdAndName(organizationId, name);
  }

  @Override
  public boolean existsById(UUID organizationId, UUID id) {
    return this.bankJpaRepository.existsByOrganizationIdAndId(organizationId, id);
  }

  @Override
  public Optional<Bank> getById(UUID organizationId, UUID id) {
    return this.bankJpaRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(BankEntity::toBank);
  }

  @Override
  public List<Bank> getAll(UUID organizationId) {
    return this.bankJpaRepository.findAllByOrganizationId(organizationId).stream()
        .map(BankEntity::toBank)
        .toList();
  }
}
