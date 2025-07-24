package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository
    extends JpaRepository<TransactionEntity, UUID>,
        PagingAndSortingRepository<TransactionEntity, UUID> {

  Page<TransactionEntity> findByAccountIdOrderByDateTimeDesc(UUID accountId, Pageable pageable);

  List<TransactionEntity> findByAccountIdAndDueDateBetween(
      UUID accountId, LocalDate from, LocalDate to);

  boolean existsByParentIdAndDueDate(UUID parentId, LocalDate dueDate);

  Optional<TransactionEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);
}
