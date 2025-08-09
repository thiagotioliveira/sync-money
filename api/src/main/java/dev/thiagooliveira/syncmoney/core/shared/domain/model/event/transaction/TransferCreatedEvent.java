package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransferCreatedEvent implements DomainEvent {
  private final UUID id;
  private final UUID organizationId;
  private final UUID sourceAccountId;
  private final UUID targetAccountId;
  private final BigDecimal sourceAmount;
  private final BigDecimal targetAmount;
  private final UUID sourceCategoryId;
  private final UUID targetCategoryId;
  private final UUID userId;
  private final OffsetDateTime dateTime;

  public TransferCreatedEvent(
      UUID id,
      UUID organizationId,
      UUID sourceAccountId,
      UUID targetAccountId,
      BigDecimal sourceAmount,
      BigDecimal targetAmount,
      UUID sourceCategoryId,
      UUID targetCategoryId,
      UUID userId,
      OffsetDateTime dateTime) {
    this.id = id;
    this.organizationId = organizationId;
    this.sourceAccountId = sourceAccountId;
    this.targetAccountId = targetAccountId;
    this.sourceAmount = sourceAmount;
    this.targetAmount = targetAmount;
    this.sourceCategoryId = sourceCategoryId;
    this.targetCategoryId = targetCategoryId;
    this.userId = userId;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "TransferCreatedEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", sourceAccountId="
        + sourceAccountId
        + ", targetAccountId="
        + targetAccountId
        + ", sourceAmount="
        + sourceAmount
        + ", targetAmount="
        + targetAmount
        + ", sourceCategoryId="
        + sourceCategoryId
        + ", targetCategoryId="
        + targetCategoryId
        + ", userId="
        + userId
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

  public UUID getSourceAccountId() {
    return sourceAccountId;
  }

  public UUID getTargetAccountId() {
    return targetAccountId;
  }

  public BigDecimal getSourceAmount() {
    return sourceAmount;
  }

  public BigDecimal getTargetAmount() {
    return targetAmount;
  }

  public UUID getSourceCategoryId() {
    return sourceCategoryId;
  }

  public UUID getTargetCategoryId() {
    return targetCategoryId;
  }

  public UUID getUserId() {
    return userId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
