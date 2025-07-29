package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.PayableReceivableEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayableReceivableJpaRepository
    extends JpaRepository<PayableReceivableEntity, UUID> {

  @Query(
      """
          SELECT pr FROM PayableReceivableEntity pr
        WHERE pr.accountId = :accountId
        AND (pr.startDate <= :from OR (pr.startDate >= :from AND pr.startDate <= :to))
              AND pr.recurring = true
      """)
  List<PayableReceivableEntity>
      findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          @Param("accountId") UUID accountId,
          @Param("from") LocalDate from,
          @Param("to") LocalDate to);

  Optional<PayableReceivableEntity> findByOrganizationIdAndId(UUID organizationId, UUID id);
}
