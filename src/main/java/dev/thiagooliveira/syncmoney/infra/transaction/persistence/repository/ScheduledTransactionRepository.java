package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.ScheduledTransactionEnriched;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduledTransactionRepository
    extends JpaRepository<ScheduledTransactionEntity, UUID> {
  @Query(
      value =
          """
              SELECT CAST(pr.id AS VARCHAR) as id,
                     CAST(pr.template_id AS VARCHAR) as template_id,
                     CAST(prt.account_id AS VARCHAR) as account_id,
                     CAST(c.id AS VARCHAR) as category_id,
                     c.name as category_name,
                     c.type as category_type,
                     prt.description as description,
                     pr.amount as amount,
                     pr.due_date as due_date,
                     prt.type as type,
                     pr.status as status,
                     prt.frequency as frequency,
                     pr.installment_number as installment_number,
                     prt.installment_total as installment_total,
                     CAST(pr.transaction_id AS VARCHAR) as transaction_id
                         FROM scheduled_transactions pr
                         INNER JOIN scheduled_transaction_templates prt ON pr.template_id = prt.id
                         INNER JOIN accounts a ON prt.account_id = a.id
                         INNER JOIN categories c ON prt.category_id = c.id
                         WHERE a.id = :accountId
                         AND pr.due_date >= :from AND pr.due_date <= :to
                      ORDER BY pr.due_date ASC
          """,
      nativeQuery = true)
  List<ScheduledTransactionEnriched> findByAccountId(
      @Param("accountId") UUID accountId, @Param("from") LocalDate from, @Param("to") LocalDate to);

  boolean existsByTemplateIdAndDueDateOriginal(UUID templateId, LocalDate dueDateOriginal);
}
