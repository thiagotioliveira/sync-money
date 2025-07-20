package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.ScheduledTransactionTemplateEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduledTransactionTemplateRepository
    extends JpaRepository<ScheduledTransactionTemplateEntity, UUID> {

  @Query(
      """
          SELECT prt FROM ScheduledTransactionTemplateEntity prt
        WHERE prt.accountId = :accountId
        AND (prt.startDate <= :from OR (prt.startDate >= :from AND prt.startDate <= :to))
        AND prt.recurring is true
      """)
  List<ScheduledTransactionTemplateEntity>
      findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          @Param("accountId") UUID accountId,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to);
}
