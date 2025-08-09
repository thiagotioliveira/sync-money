package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionPaidEvent implements DomainEvent {
  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final UUID userId;
  private final UUID categoryId;
  private final CategoryType categoryType;
  private final OffsetDateTime dateTime;
  private final BigDecimal amount;

  public TransactionPaidEvent(
      UUID id,
      UUID organizationId,
      UUID accountId,
      UUID userId,
      UUID categoryId,
      CategoryType categoryType,
      OffsetDateTime dateTime,
      BigDecimal amount) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.userId = userId;
    this.categoryId = categoryId;
    this.categoryType = categoryType;
    this.dateTime = dateTime;
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "TransactionPaidEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", accountId="
        + accountId
        + ", userId="
        + userId
        + ", categoryId="
        + categoryId
        + ", categoryType="
        + categoryType
        + ", dateTime="
        + dateTime
        + ", amount="
        + amount
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
