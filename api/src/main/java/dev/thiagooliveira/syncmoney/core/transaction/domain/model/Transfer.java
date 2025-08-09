package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransferCreatedEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transfer implements AggregateRoot {
  private UUID id;
  private UUID organizationId;
  private UUID sourceAccountId;
  private UUID targetAccountId;
  private BigDecimal sourceAmount;
  private BigDecimal targetAmount;
  private UUID sourceCategoryId;
  private UUID targetCategoryId;
  private UUID userId;
  private OffsetDateTime dateTime;

  private Transfer() {}

  public static Transfer restore(
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
    var transfer = new Transfer();
    transfer.id = id;
    transfer.organizationId = organizationId;
    transfer.sourceAccountId = sourceAccountId;
    transfer.targetAccountId = targetAccountId;
    transfer.sourceAmount = sourceAmount;
    transfer.targetAmount = targetAmount;
    transfer.sourceCategoryId = sourceCategoryId;
    transfer.targetCategoryId = targetCategoryId;
    transfer.userId = userId;
    transfer.dateTime = dateTime;
    return transfer;
  }

  public Transfer created() {
    DomainEventPublisher.addEvent(
        new TransferCreatedEvent(
            this.getId(),
            this.getOrganizationId(),
            this.getSourceAccountId(),
            this.getTargetAccountId(),
            this.getSourceAmount(),
            this.getTargetAmount(),
            this.getSourceCategoryId(),
            this.getTargetCategoryId(),
            this.getUserId(),
            this.getDateTime()));
    return this;
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
