package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "scheduled_transactions")
public class ScheduledTransactionEntity {
  @Id private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "template_id", nullable = false)
  private ScheduledTransactionTemplateEntity template;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private LocalDate dueDateOriginal;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ScheduledTransactionStatus status;

  @Column private Integer installmentNumber;

  @Column private UUID transactionId;

  public ScheduledTransactionEntity() {}

  //  public static ScheduledTransactionEntity from(CreateScheduledTransactionInput input) {
  //    var entity = new ScheduledTransactionEntity();
  //    entity.setId(UUID.randomUUID());
  //  }

  public static ScheduledTransactionEntity from(ScheduledTransaction scheduledTransaction) {
    var entity = new ScheduledTransactionEntity();
    entity.setId(scheduledTransaction.id());
    entity.setTemplate(new ScheduledTransactionTemplateEntity());
    entity.getTemplate().setId(scheduledTransaction.templateId());
    entity.setDueDate(scheduledTransaction.dueDate());
    entity.setDueDateOriginal(scheduledTransaction.dueDate());
    entity.setAmount(scheduledTransaction.amount());
    entity.setStatus(scheduledTransaction.status());
    entity.setInstallmentNumber(scheduledTransaction.installmentNumber().orElse(null));
    entity.setTransactionId(scheduledTransaction.transactionId().orElse(null));
    return entity;
  }

  public ScheduledTransaction toScheduledTransaction() {
    return new ScheduledTransaction(
        this.id,
        this.template.getId(),
        this.template.getAccountId(),
        this.template.getCategoryId(),
        this.template.getDescription(),
        this.amount,
        this.dueDate,
        this.template.getType(),
        this.status,
        this.template.getFrequency(),
        Optional.ofNullable(this.installmentNumber),
        Optional.ofNullable(this.template.getInstallmentTotal()),
        Optional.ofNullable(this.transactionId));
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getDueDateOriginal() {
    return dueDateOriginal;
  }

  public void setDueDateOriginal(LocalDate dueDateOriginal) {
    this.dueDateOriginal = dueDateOriginal;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Integer getInstallmentNumber() {
    return installmentNumber;
  }

  public void setInstallmentNumber(Integer installmentNumber) {
    this.installmentNumber = installmentNumber;
  }

  public ScheduledTransactionStatus getStatus() {
    return status;
  }

  public void setStatus(ScheduledTransactionStatus status) {
    this.status = status;
  }

  public ScheduledTransactionTemplateEntity getTemplate() {
    return template;
  }

  public void setTemplate(ScheduledTransactionTemplateEntity template) {
    this.template = template;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }
}
