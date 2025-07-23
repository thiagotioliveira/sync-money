package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionCreatedEvent implements Event {

  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final OffsetDateTime dateTime;
  private final String description;
  private final UUID categoryId;
  private final CategoryType categoryType;
  private final BigDecimal amount;

  public TransactionCreatedEvent(
      UUID organizationId, CategoryType categoryType, Transaction transaction) {
    this.id = transaction.id();
    this.organizationId = organizationId;
    this.accountId = transaction.accountId();
    this.dateTime = transaction.dateTime();
    this.description = transaction.description();
    this.categoryId = transaction.categoryId();
    this.categoryType = categoryType;
    this.amount = transaction.amount();
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }

  public UUID getId() {
    return id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public CategoryType getCategoryType() {
    return categoryType;
  }
}
