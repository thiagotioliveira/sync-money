package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionScheduledCreatedEvent implements DomainEvent {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final UUID categoryId;
  private final LocalDate dueDate;
  private final BigDecimal amount;
  private final OffsetDateTime dateTime;

  public TransactionScheduledCreatedEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      UUID categoryId,
      LocalDate dueDate,
      BigDecimal amount,
      OffsetDateTime dateTime) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.categoryId = categoryId;
    this.dueDate = dueDate;
    this.amount = amount;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "TransactionScheduledCreatedEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", accountId="
        + accountId
        + ", categoryId="
        + categoryId
        + ", dueDate="
        + dueDate
        + ", amount="
        + amount
        + ", dateTime="
        + dateTime
        + '}';
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

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
