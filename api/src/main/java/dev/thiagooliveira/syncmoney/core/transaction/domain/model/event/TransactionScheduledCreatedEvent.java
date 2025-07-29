package dev.thiagooliveira.syncmoney.core.transaction.domain.model.event;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Installment;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TransactionScheduledCreatedEvent implements Event {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final UUID categoryId;
  private final LocalDate dueDate;
  private final BigDecimal amount;

  public TransactionScheduledCreatedEvent(Installment installment) {
    this.id = installment.getId();
    this.organizationId = installment.getOrganizationId();
    this.accountId = installment.getAccountId();
    this.categoryId = installment.getCategoryId();
    this.dueDate = installment.getDueDate();
    this.amount = installment.getAmount();
  }

  public UUID getId() {
    return id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public UUID getCategoryId() {
    return categoryId;
  }
}
