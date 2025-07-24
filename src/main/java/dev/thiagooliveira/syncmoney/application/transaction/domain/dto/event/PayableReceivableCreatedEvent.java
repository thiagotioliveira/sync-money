package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.PayableReceivable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PayableReceivableCreatedEvent implements Event {
  private final UUID payableReceivableId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean recurring;
  private final List<UUID> installments;

  public PayableReceivableCreatedEvent(
      PayableReceivable payableReceivable, List<UUID> installments) {
    this.payableReceivableId = payableReceivable.id();
    this.startDate = payableReceivable.startDate();
    this.endDate = payableReceivable.endDate();
    this.recurring = payableReceivable.recurring();
    this.installments = installments;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public List<UUID> getInstallments() {
    return installments;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public UUID getPayableReceivableId() {
    return payableReceivableId;
  }
}
