package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionPaidEvent implements Event {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final UUID userId;
  private final UUID categoryId;
  private final CategoryType categoryType;
  private final OffsetDateTime dateTime;
  private final BigDecimal amount;

  public TransactionPaidEvent(Transaction transaction, CategoryType categoryType) {
    this.id = transaction.getId();
    this.organizationId = transaction.getOrganizationId();
    this.userId = transaction.getUserId();
    this.accountId = transaction.getAccountId();
    this.categoryId = transaction.getCategoryId();
    this.categoryType = categoryType;
    this.dateTime = transaction.getDateTime();
    this.amount = transaction.getAmount();
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

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public CategoryType getCategoryType() {
    return categoryType;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public UUID getUserId() {
    return userId;
  }
}
