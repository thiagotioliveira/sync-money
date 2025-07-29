package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionJpaRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

  private final TransactionJpaRepository transactionJpaRepository;

  public TransactionRepositoryAdapter(TransactionJpaRepository transactionJpaRepository) {
    this.transactionJpaRepository = transactionJpaRepository;
  }

  @Override
  public Transaction create(CreateTransactionInput input, Category category) {
    return this.transactionJpaRepository
        .save(TransactionEntity.createPaid(input))
        .toTransactionPaidCreated(category.getType());
  }

  @Override
  public List<Installment> createInstallments(List<Installment> installments) {
    return installments.stream().map(this::createInstallment).toList();
  }

  @Override
  public Installment createInstallment(Installment installment) {
    return this.transactionJpaRepository
        .save(TransactionEntity.createScheduled(installment))
        .toInstallmentCreated();
  }

  @Override
  public Transaction update(Transaction transaction) {
    return this.transactionJpaRepository
        .save(TransactionEntity.restore(transaction))
        .toTransactionUpdated();
  }

  @Override
  public Transaction pay(Transaction transaction, Category category) {
    return this.transactionJpaRepository
        .save(TransactionEntity.restore(transaction))
        .toTransactionPaidCreated(category.getType());
  }

  @Override
  public List<Transaction> getByAccountAndYearMonth(UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.transactionJpaRepository
        .findByAccountIdAndDueDateBetweenOrderByDueDateDesc(accountId, from, to)
        .stream()
        .map(TransactionEntity::toTransaction)
        .toList();
  }

  @Override
  public List<Transaction> findByParentId(UUID parentId) {
    return this.transactionJpaRepository.findByParentIdOrderByDueDateDesc(parentId).stream()
        .map(TransactionEntity::toTransaction)
        .toList();
  }

  //  @Override
  //  public Page<Transaction> findByAccountIdOrderByDueDateDesc(UUID accountId, Pageable pageable)
  // {
  //    var page =
  //        this.transactionRepository.findByAccountIdOrderByDateTimeDesc(
  //            accountId, PageRequest.of(pageable.pageNumber(), pageable.pageSize()));
  //    return new Page<>(
  //        page.getContent().stream().map(TransactionEntity::toTransaction).toList(),
  //        page.getNumber(),
  //        page.getSize(),
  //        page.getTotalElements(),
  //        page.getTotalPages(),
  //        page.isFirst(),
  //        page.isLast());
  //  }

  @Override
  public boolean existsByParentIdAndDueDate(UUID parentId, LocalDate dueDate) {
    return this.transactionJpaRepository.existsByParentIdAndDueDate(parentId, dueDate);
  }

  @Override
  public Optional<Transaction> getById(UUID organizationId, UUID accountId, UUID id) {
    return this.transactionJpaRepository
        .findByOrganizationIdAndAccountIdAndId(organizationId, accountId, id)
        .map(TransactionEntity::toTransaction);
  }
}
