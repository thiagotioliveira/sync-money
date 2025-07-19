package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionAdapter implements TransactionPort {

  private final TransactionRepository transactionRepository;

  public TransactionAdapter(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction create(CreateTransactionInput input) {
    return this.transactionRepository.save(TransactionEntity.from(input)).toTransaction();
  }
}
