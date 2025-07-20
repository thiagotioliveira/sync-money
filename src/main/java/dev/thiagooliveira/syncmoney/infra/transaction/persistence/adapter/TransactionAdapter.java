package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionRepository;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
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

  @Override
  public Page<TransactionEnriched> findByAccountId(UUID accountId, Pageable pageable) {
    var page =
        this.transactionRepository.findByAccountId(
            accountId, PageRequest.of(pageable.pageNumber(), pageable.pageSize()));
    return new Page<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast());
  }
}
