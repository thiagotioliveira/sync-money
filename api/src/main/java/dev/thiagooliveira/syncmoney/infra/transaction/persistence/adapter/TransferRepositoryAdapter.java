package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransferInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransferRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransferEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransferJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TransferRepositoryAdapter implements TransferRepository {

  private final TransferJpaRepository transferJpaRepository;

  public TransferRepositoryAdapter(TransferJpaRepository transferJpaRepository) {
    this.transferJpaRepository = transferJpaRepository;
  }

  @Override
  public Transfer create(CreateTransferInput input) {
    return this.transferJpaRepository.save(TransferEntity.create(input)).toTransferCreated();
  }
}
