package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransferCreatedEvent;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransferInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfers")
public class TransferEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID sourceAccountId;

  @Column(nullable = false)
  private UUID targetAccountId;

  @Column(nullable = false)
  private UUID organizationId;

  @Column private UUID userId;

  @Column private OffsetDateTime dateTime;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "source_category_id", nullable = false)
  private CategoryEntity sourceCategory;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "target_category_id", nullable = false)
  private CategoryEntity targetCategory;

  @Column(nullable = false)
  private BigDecimal sourceAmount;

  @Column(nullable = false)
  private BigDecimal targetAmount;

  public TransferEntity() {}

  public static TransferEntity create(CreateTransferInput input) {
    var entity = new TransferEntity();
    entity.id = UUID.randomUUID();
    entity.dateTime = OffsetDateTime.now();
    entity.sourceAccountId = input.sourceAccountId();
    entity.targetAccountId = input.targetAccountId();
    entity.organizationId = input.organizationId();
    entity.userId = input.userId();
    entity.sourceCategory = new CategoryEntity();
    entity.sourceCategory.setId(input.sourceCategoryId());
    entity.targetCategory = new CategoryEntity();
    entity.targetCategory.setId(input.targetCategoryId());
    entity.sourceAmount = input.sourceAmount();
    entity.targetAmount = input.targetAmount();
    return entity;
  }

  public Transfer toTransfer() {
    return Transfer.restore(
        this.id,
        this.organizationId,
        this.sourceAccountId,
        this.targetAccountId,
        this.sourceAmount,
        this.targetAmount,
        this.sourceAccountId,
        this.targetAccountId,
        this.userId,
        this.dateTime);
  }

  public Transfer toTransferCreated() {
    var transfer = toTransfer();
    transfer.registerEvent(
        new TransferCreatedEvent(
            transfer.getId(),
            transfer.getOrganizationId(),
            transfer.getSourceAccountId(),
            transfer.getTargetAccountId(),
            transfer.getSourceAmount(),
            transfer.getTargetAmount(),
            transfer.getSourceCategoryId(),
            transfer.getTargetCategoryId(),
            transfer.getUserId(),
            transfer.getDateTime()));
    return transfer;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getSourceAccountId() {
    return sourceAccountId;
  }

  public void setSourceAccountId(UUID sourceAccountId) {
    this.sourceAccountId = sourceAccountId;
  }

  public UUID getTargetAccountId() {
    return targetAccountId;
  }

  public void setTargetAccountId(UUID targetAccountId) {
    this.targetAccountId = targetAccountId;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public CategoryEntity getSourceCategory() {
    return sourceCategory;
  }

  public void setSourceCategory(CategoryEntity sourceCategory) {
    this.sourceCategory = sourceCategory;
  }

  public CategoryEntity getTargetCategory() {
    return targetCategory;
  }

  public void setTargetCategory(CategoryEntity targetCategory) {
    this.targetCategory = targetCategory;
  }

  public BigDecimal getSourceAmount() {
    return sourceAmount;
  }

  public void setSourceAmount(BigDecimal sourceAmount) {
    this.sourceAmount = sourceAmount;
  }

  public BigDecimal getTargetAmount() {
    return targetAmount;
  }

  public void setTargetAmount(BigDecimal targetAmount) {
    this.targetAmount = targetAmount;
  }
}
