package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.AccountSummaryEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.AccountSummaryId;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.AccountSummaryJpaRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountSummaryRepositoryAdapter implements AccountSummaryRepository {

  private final AccountSummaryJpaRepository accountSummaryJpaRepository;

  public AccountSummaryRepositoryAdapter(AccountSummaryJpaRepository accountSummaryJpaRepository) {
    this.accountSummaryJpaRepository = accountSummaryJpaRepository;
  }

  @Override
  public int deleteTheFuture(UUID accountId, YearMonth yearMonth) {
    return this.accountSummaryJpaRepository.deleteById_AccountIdAndId_YearMonthGreaterThan(
        accountId, yearMonth.atDay(1));
  }

  @Override
  public Optional<AccountSummary> findByAccountIdAndYearMonth(UUID accountId, YearMonth yearMonth) {
    return this.accountSummaryJpaRepository
        .findById(new AccountSummaryId(accountId, yearMonth.atDay(1)))
        .map(AccountSummaryEntity::toAccountSummary);
  }

  @Override
  public Optional<AccountSummary> findLastOne(UUID accountId) {
    return this.accountSummaryJpaRepository
        .findTopById_AccountIdOrderById_YearMonthAsc(accountId)
        .map(AccountSummaryEntity::toAccountSummary);
  }

  @Override
  public List<AccountSummary> findAllByAccountIdAfter(UUID accountId, YearMonth yearMonth) {
    return this.accountSummaryJpaRepository
        .findAllById_AccountIdAndId_YearMonthGreaterThanOrderById_YearMonthDesc(
            accountId, yearMonth.atEndOfMonth())
        .stream()
        .map(AccountSummaryEntity::toAccountSummary)
        .toList();
  }

  @Override
  public AccountSummary save(AccountSummary accountSummary) {
    return this.accountSummaryJpaRepository
        .save(AccountSummaryEntity.from(accountSummary))
        .toAccountSummary();
  }
}
