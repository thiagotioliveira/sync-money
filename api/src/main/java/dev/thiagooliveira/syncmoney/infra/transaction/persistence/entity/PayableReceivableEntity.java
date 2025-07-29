package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.PayableReceivableCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.PayableReceivableUpdatedEvent;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "payable_receivable")
public class PayableReceivableEntity {

  @Id private UUID id;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(name = "category_id", nullable = false)
  private UUID categoryId;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "recurring", nullable = false)
  private boolean recurring;

  @Column(name = "installment_total")
  private Integer installmentTotal;

  public PayableReceivableEntity() {}

  public static PayableReceivableEntity from(CreatePayableReceivableInput input) {
    PayableReceivableEntity entity = new PayableReceivableEntity();
    entity.setId(UUID.randomUUID());
    entity.setAccountId(input.accountId());
    entity.setOrganizationId(input.organizationId());
    entity.setCategoryId(input.categoryId());
    entity.setDescription(input.description());
    entity.setStartDate(input.startDate());
    entity.setAmount(input.amount());
    entity.setRecurring(input.recurring());
    entity.setInstallmentTotal(input.installmentTotal().orElse(null));
    input
        .installmentTotal()
        .ifPresent(total -> entity.setEndDate(input.startDate().plusMonths(total)));
    return entity;
  }

  public static PayableReceivableEntity restore(PayableReceivable payableReceivable) {
    PayableReceivableEntity entity = new PayableReceivableEntity();
    entity.setId(payableReceivable.getId());
    entity.setAccountId(payableReceivable.getAccountId());
    entity.setOrganizationId(payableReceivable.getOrganizationId());
    entity.setCategoryId(payableReceivable.getCategoryId());
    entity.setDescription(payableReceivable.getDescription());
    entity.setStartDate(payableReceivable.getStartDate());
    entity.setAmount(payableReceivable.getAmount());
    entity.setRecurring(payableReceivable.isRecurring());
    entity.setInstallmentTotal(payableReceivable.getInstallmentTotal().orElse(null));
    return entity;
  }

  public PayableReceivable toPayableReceivable() {
    return PayableReceivable.restore(
        this.id,
        this.accountId,
        this.organizationId,
        this.categoryId,
        this.description,
        this.amount,
        this.startDate,
        this.endDate,
        this.recurring,
        Optional.ofNullable(this.installmentTotal));
  }

  public PayableReceivable toPayableReceivableCreated() {
    var payableReceivable = toPayableReceivable();
    payableReceivable.registerEvent(
        new PayableReceivableCreatedEvent(
            payableReceivable.getId(),
            payableReceivable.getOrganizationId(),
            payableReceivable.getAccountId(),
            payableReceivable.getStartDate(),
            payableReceivable.getEndDate(),
            payableReceivable.isRecurring()));
    return payableReceivable;
  }

  public PayableReceivable toPayableReceivableUpdated() {
    var payableReceivable = toPayableReceivable();
    payableReceivable.registerEvent(
        new PayableReceivableUpdatedEvent(
            payableReceivable.getId(),
            payableReceivable.getOrganizationId(),
            payableReceivable.getAccountId(),
            payableReceivable.getStartDate(),
            payableReceivable.getEndDate(),
            payableReceivable.isRecurring()));
    return payableReceivable;
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public void setRecurring(boolean recurring) {
    this.recurring = recurring;
  }

  public Integer getInstallmentTotal() {
    return installmentTotal;
  }

  public void setInstallmentTotal(Integer installmentTotal) {
    this.installmentTotal = installmentTotal;
  }
}
