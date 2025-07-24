package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private OffsetDateTime dateTime;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column(nullable = false)
  private String description;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private PayableReceivableEntity parent;

  public TransactionEntity() {}

  public static TransactionEntity from(CreateTransactionInput input) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = UUID.randomUUID();
    entity.dateTime = input.dateTime();
    entity.dueDate = entity.dateTime.toLocalDate();
    entity.accountId = input.accountId();
    entity.organizationId = input.organizationId();
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(input.categoryId());
    entity.description = input.description();
    entity.amount = input.amount();
    entity.status = TransactionStatus.PAID;
    return entity;
  }

  public static TransactionEntity from(Installment installment) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = installment.id();
    entity.dateTime = installment.dueDate().atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    entity.dueDate = entity.dateTime.toLocalDate();
    entity.accountId = installment.accountId();
    entity.organizationId = installment.organizationId();
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(installment.categoryId());
    entity.description = installment.description();
    entity.amount = installment.amount();
    entity.status = TransactionStatus.SCHEDULED;
    entity.parent = new PayableReceivableEntity();
    entity.parent.setId(installment.parentId());
    return entity;
  }

  public Transaction toTransaction() {
    return new Transaction(
        id,
        accountId,
        organizationId,
        dateTime,
        dueDate,
        description,
        CategoryEntity.from(category),
        amount,
        status,
        parent != null ? Optional.ofNullable(parent.getId()) : Optional.empty());
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public PayableReceivableEntity getParent() {
    return parent;
  }

  public void setParent(PayableReceivableEntity parent) {
    this.parent = parent;
  }
}
