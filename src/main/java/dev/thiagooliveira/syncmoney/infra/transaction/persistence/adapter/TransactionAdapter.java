package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
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
  public List<Transaction> createScheduled(List<Installment> installments) {
    return installments.stream().map(this::createScheduled).toList();
  }

  @Override
  public Transaction createScheduled(Installment installment) {
    return this.transactionRepository.save(TransactionEntity.from(installment)).toTransaction();
  }

  @Override
  public List<Transaction> findByAccountAndYearMonth(UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.transactionRepository.findByAccountIdAndDueDateBetween(accountId, from, to).stream()
        .map(TransactionEntity::toTransaction)
        .toList();
  }

  @Override
  public Page<Transaction> findByAccountId(UUID accountId, Pageable pageable) {
    var page =
        this.transactionRepository.findByAccountIdOrderByDateTimeDesc(
            accountId, PageRequest.of(pageable.pageNumber(), pageable.pageSize()));
    return new Page<>(
        page.getContent().stream().map(TransactionEntity::toTransaction).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast());
  }

  @Override
  public boolean existsByParentIdAndDueDate(UUID parentId, LocalDate dueDate) {
    return this.transactionRepository.existsByParentIdAndDueDate(parentId, dueDate);
  }
}
