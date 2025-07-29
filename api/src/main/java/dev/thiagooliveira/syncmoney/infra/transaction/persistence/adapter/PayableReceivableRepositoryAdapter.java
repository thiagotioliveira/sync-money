package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.PayableReceivableRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.PayableReceivableJpaRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableRepositoryAdapter implements PayableReceivableRepository {
  private final PayableReceivableJpaRepository payableReceivableJpaRepository;

  public PayableReceivableRepositoryAdapter(
      PayableReceivableJpaRepository payableReceivableJpaRepository) {
    this.payableReceivableJpaRepository = payableReceivableJpaRepository;
  }

  @Override
  public PayableReceivable create(CreatePayableReceivableInput input) {
    return this.payableReceivableJpaRepository
        .save(PayableReceivableEntity.from(input))
        .toPayableReceivableCreated();
  }

  @Override
  public PayableReceivable update(PayableReceivable payableReceivable) {
    return this.payableReceivableJpaRepository
        .save(PayableReceivableEntity.restore(payableReceivable))
        .toPayableReceivableUpdated();
  }

  public List<PayableReceivable> getRecurringByAccountId(UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.payableReceivableJpaRepository
        .findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
            accountId, from, to)
        .stream()
        .map(PayableReceivableEntity::toPayableReceivable)
        .toList();
  }

  @Override
  public Optional<PayableReceivable> getById(UUID organizationId, UUID id) {
    return this.payableReceivableJpaRepository
        .findByOrganizationIdAndId(organizationId, id)
        .map(PayableReceivableEntity::toPayableReceivable);
  }
}
