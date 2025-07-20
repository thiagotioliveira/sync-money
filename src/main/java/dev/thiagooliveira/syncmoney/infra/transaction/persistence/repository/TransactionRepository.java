package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository
    extends JpaRepository<TransactionEntity, UUID>,
        PagingAndSortingRepository<TransactionEntity, UUID> {

  @Query(
      value =
          """
        SELECT
                    CAST(t.id AS VARCHAR) AS id,
                    CAST(t.account_id AS VARCHAR) as accountId,
                    t.date_time AS dateTime,
                    t.description AS description,
                    CAST(c.id AS VARCHAR) AS categoryId,
                    c.name AS categoryName,
                    c.type AS categoryType,
                    t.amount AS amount,
                    b.currency AS currency
                FROM transactions t
                JOIN categories c ON t.category_id = c.id
                JOIN accounts a ON a.id = t.account_id
                JOIN banks b ON b.id = a.bank_id
                WHERE t.account_id = :accountId
                ORDER BY t.date_time DESC
                LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
            """,
      countQuery =
          """
            SELECT COUNT(*)
                FROM
            transactions t
                JOIN categories c ON t.category_id = c.id
                JOIN accounts a ON a.id = t.account_id
                JOIN banks b ON b.id = a.bank_id
            WHERE t.account_id = :accountId
    """,
      nativeQuery = true)
  Page<TransactionEnriched> findByAccountId(@Param("accountId") UUID accountId, Pageable pageable);
}
