package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.PayableReceivable;
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

  public PayableReceivable toPayableReceivable() {
    return new PayableReceivable(
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
