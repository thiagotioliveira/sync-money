package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Frequency;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "scheduled_transaction_templates")
public class ScheduledTransactionTemplateEntity {

  @Id private UUID id;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(name = "category_id", nullable = false)
  private UUID categoryId;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private ScheduledTransactionType type;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Column(name = "recurring", nullable = false)
  private boolean recurring;

  @Enumerated(EnumType.STRING)
  @Column(name = "frequency")
  private Frequency frequency;

  @Column(name = "installment_total")
  private Integer installmentTotal;

  public ScheduledTransactionTemplateEntity() {}

  public static ScheduledTransactionTemplateEntity from(CreateScheduledTransactionInput input) {
    ScheduledTransactionTemplateEntity entity = new ScheduledTransactionTemplateEntity();
    entity.setId(UUID.randomUUID());
    entity.setAccountId(input.accountId());
    entity.setCategoryId(input.categoryId());
    entity.setDescription(input.description());
    entity.setType(input.type());
    entity.setStartDate(input.startDate());
    entity.setEnabled(true);
    entity.setFrequency(Frequency.MONTHLY);
    entity.setInstallmentTotal(input.installmentTotal().orElse(null));
    entity.setRecurring(input.recurring());
    entity.setAmount(input.amount());
    return entity;
  }

  public ScheduledTransactionTemplate toScheduledTransactionTemplate() {
    return new ScheduledTransactionTemplate(
        this.id,
        this.accountId,
        this.categoryId,
        this.description,
        this.amount,
        this.type,
        this.startDate,
        this.endDate,
        this.enabled,
        this.recurring,
        this.frequency,
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

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(Frequency frequency) {
    this.frequency = frequency;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getInstallmentTotal() {
    return installmentTotal;
  }

  public void setInstallmentTotal(Integer installmentTotal) {
    this.installmentTotal = installmentTotal;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public void setRecurring(boolean recurring) {
    this.recurring = recurring;
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

  public ScheduledTransactionType getType() {
    return type;
  }

  public void setType(ScheduledTransactionType type) {
    this.type = type;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
