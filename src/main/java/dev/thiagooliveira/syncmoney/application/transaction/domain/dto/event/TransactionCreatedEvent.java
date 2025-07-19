package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.event.domain.dto.Event;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionCreatedEvent implements Event {

  private UUID id;
  private UUID organizationId;
  private UUID accountId;
  private OffsetDateTime dateTime;
  private String description;
  private UUID categoryId;
  private CategoryType categoryType;
  private BigDecimal amount;

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

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public CategoryType getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(CategoryType categoryType) {
    this.categoryType = categoryType;
  }
}
