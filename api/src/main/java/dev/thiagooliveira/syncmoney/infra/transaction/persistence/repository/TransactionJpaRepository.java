package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionJpaRepository
    extends JpaRepository<TransactionEntity, UUID>,
        PagingAndSortingRepository<TransactionEntity, UUID> {

  List<TransactionEntity> findByAccountIdAndDueDateBetweenOrderByDueDateDesc(
      UUID accountId, LocalDate from, LocalDate to);

  List<TransactionEntity> findByParentIdOrderByDueDateDesc(UUID parentId);

  boolean existsByParentIdAndDueDate(UUID parentId, LocalDate dueDate);

  Optional<TransactionEntity> findByOrganizationIdAndAccountIdAndId(
      UUID organizationId, UUID accountId, UUID id);
}
