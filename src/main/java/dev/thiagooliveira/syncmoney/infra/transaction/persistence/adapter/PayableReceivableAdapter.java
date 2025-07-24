package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.PayableReceivablePort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.PayableReceivableRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableAdapter implements PayableReceivablePort {
  private final PayableReceivableRepository payableReceivableRepository;

  public PayableReceivableAdapter(PayableReceivableRepository payableReceivableRepository) {
    this.payableReceivableRepository = payableReceivableRepository;
  }

  @Override
  public PayableReceivable create(CreatePayableReceivableInput input) {
    return this.payableReceivableRepository
        .save(PayableReceivableEntity.from(input))
        .toPayableReceivable();
  }

  public List<PayableReceivable> findRecurringByAccountId(UUID accountId, YearMonth yearMonth) {
    var from = yearMonth.atDay(1);
    var to = yearMonth.atEndOfMonth();
    return this.payableReceivableRepository
        .findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
            accountId, from, to)
        .stream()
        .map(PayableReceivableEntity::toPayableReceivable)
        .toList();
  }
}
