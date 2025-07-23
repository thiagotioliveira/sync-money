package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private UUID accountId;

  @Column(nullable = false)
  private OffsetDateTime dateTime;

  @Column(nullable = false)
  private String description;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @Column(nullable = false)
  private BigDecimal amount;

  public TransactionEntity() {}

  public static TransactionEntity from(CreateTransactionInput input) {
    TransactionEntity entity = new TransactionEntity();
    entity.id = UUID.randomUUID();
    entity.dateTime = input.dateTime();
    entity.accountId = input.accountId();
    entity.setCategory(new CategoryEntity());
    entity.getCategory().setId(input.categoryId());
    entity.description = input.description();
    entity.amount = input.amount();
    return entity;
  }

  public Transaction toTransaction() {
    return new Transaction(id, accountId, dateTime, description, CategoryEntity.from(category), amount);
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
}
