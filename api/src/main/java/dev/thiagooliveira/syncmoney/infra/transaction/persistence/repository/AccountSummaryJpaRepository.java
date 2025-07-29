package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.AccountSummaryEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.AccountSummaryId;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSummaryJpaRepository extends JpaRepository<AccountSummaryEntity, Long> {

  int deleteById_AccountIdAndId_YearMonthGreaterThan(UUID accountId, LocalDate yearMonth);

  Optional<AccountSummaryEntity> findById(AccountSummaryId id);

  Optional<AccountSummaryEntity> findTopById_AccountIdOrderById_YearMonthAsc(UUID accountId);

  List<AccountSummaryEntity> findAllById_AccountIdAndId_YearMonthGreaterThanOrderById_YearMonthDesc(
      UUID idAccountId, LocalDate yearMonth);
}
