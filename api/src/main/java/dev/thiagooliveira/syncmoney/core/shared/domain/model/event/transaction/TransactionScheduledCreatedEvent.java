package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
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

  public TransactionScheduledCreatedEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      UUID categoryId,
      LocalDate dueDate,
      BigDecimal amount) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.categoryId = categoryId;
    this.dueDate = dueDate;
    this.amount = amount;
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
