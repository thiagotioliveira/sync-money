package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionPaidEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionScheduledCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionUpdatedEvent;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.TransactionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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

  @Column private UUID userId;

  @Column private OffsetDateTime dateTime;

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

  public static TransactionEntity createPaid(CreateTransactionInput input) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = UUID.randomUUID();
    entity.dateTime = input.dateTime();
    entity.dueDate = entity.dateTime.toLocalDate();
    entity.accountId = input.accountId();
    entity.organizationId = input.organizationId();
    entity.userId = input.userId();
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(input.categoryId());
    entity.description = input.description();
    entity.amount = input.amount();
    entity.status = TransactionStatus.PAID;
    return entity;
  }

  public static TransactionEntity createScheduled(Installment installment) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = installment.getId();
    entity.dateTime = null;
    entity.dueDate = installment.getDueDate();
    entity.accountId = installment.getAccountId();
    entity.organizationId = installment.getOrganizationId();
    entity.userId = null;
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(installment.getCategoryId());
    entity.description = installment.getDescription();
    entity.amount = installment.getAmount();
    entity.status = TransactionStatus.SCHEDULED;
    entity.parent = new PayableReceivableEntity();
    entity.parent.setId(installment.getParentId());
    return entity;
  }

  public static TransactionEntity restore(Transaction transaction) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = transaction.getId();
    entity.dateTime = transaction.getDateTime();
    entity.dueDate = transaction.getDueDate();
    entity.accountId = transaction.getAccountId();
    entity.organizationId = transaction.getOrganizationId();
    entity.userId = transaction.getUserId();
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(transaction.getCategoryId());
    entity.description = transaction.getDescription();
    entity.amount = transaction.getAmount();
    entity.status = transaction.getStatus();
    if (transaction.getParentId().isPresent()) {
      PayableReceivableEntity parentEntity = new PayableReceivableEntity();
      parentEntity.setId(transaction.getParentId().get());
      entity.setParent(parentEntity);
    }
    return entity;
  }

  public Installment toInstallmentCreated() {
    var installment =
        Installment.restore(
            id,
            accountId,
            organizationId,
            parent.getId(),
            dueDate,
            description,
            category.getId(),
            amount,
            status);
    installment.registerEvent(new TransactionScheduledCreatedEvent(installment));
    return installment;
  }

  public Transaction toTransaction() {
    return Transaction.restore(
        id,
        accountId,
        organizationId,
        userId,
        dateTime,
        dueDate,
        description,
        category.getId(),
        amount,
        status,
        parent != null ? Optional.of(parent.getId()) : Optional.empty());
  }

  public Transaction toTransactionPaidCreated(CategoryType categoryType) {
    var transaction = toTransaction();
    transaction.registerEvent(new TransactionPaidEvent(transaction, categoryType));
    return transaction;
  }

  public Transaction toTransactionUpdated() {
    var transaction = toTransaction();
    transaction.registerEvent(new TransactionUpdatedEvent(transaction));
    return transaction;
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

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
