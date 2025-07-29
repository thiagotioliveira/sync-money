package dev.thiagooliveira.syncmoney.core.transaction.domain.model.event;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import java.time.LocalDate;
import java.util.UUID;

public class PayableReceivableUpdatedEvent implements Event {
  private final UUID payableReceivableId;
  private final UUID organizationId;
  private final UUID accountId;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final boolean recurring;

  public PayableReceivableUpdatedEvent(PayableReceivable payableReceivable) {
    this.payableReceivableId = payableReceivable.getId();
    this.accountId = payableReceivable.getAccountId();
    this.startDate = payableReceivable.getStartDate();
    this.endDate = payableReceivable.getEndDate();
    this.recurring = payableReceivable.isRecurring();
    this.organizationId = payableReceivable.getOrganizationId();
  }

  public LocalDate getEndDate() {
    return endDate;
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

  public UUID getAccountId() {
    return accountId;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }
}
