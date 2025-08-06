package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class Transaction extends AggregateRoot {
  private UUID id;
  private UUID accountId;
  private UUID organizationId;
  private UUID userId;
  private OffsetDateTime dateTime;
  private LocalDate dueDate;
  private String description;
  private UUID categoryId;
  private BigDecimal amount;
  private TransactionStatus status;
  private Optional<UUID> parentId;
  private Optional<UUID> transferId;

  public boolean isScheduled() {
    return this.status == TransactionStatus.SCHEDULED;
  }

  public boolean isPaid() {
    return this.status == TransactionStatus.PAID;
  }

  public Transaction pay(UUID userId, OffsetDateTime dateTime, Optional<BigDecimal> amount) {
    if (!isScheduled()) {
      throw BusinessLogicException.badRequest("transaction is not scheduled");
    }
    if (dateTime.isAfter(OffsetDateTime.now())) {
      throw BusinessLogicException.badRequest("transaction is not yet due");
    }
    this.userId = userId;
    this.dateTime = dateTime;
    this.amount = amount.orElse(this.amount);
    this.status = TransactionStatus.PAID;
    return this;
  }

  public Transaction setTransfer(UUID transferId) {
    this.transferId = Optional.of(transferId);
    return this;
  }

  public Transaction update(Optional<LocalDate> dueDate, Optional<BigDecimal> amount) {
    if (!isScheduled()) {
      throw BusinessLogicException.badRequest("transaction is not scheduled");
    }
    dueDate.ifPresent(
        d -> {
          if (d.getMonth() != this.dueDate.getMonth() || d.getYear() != this.dueDate.getYear()) {
            throw BusinessLogicException.badRequest("transaction due date cannot be changed");
          }
        });
    amount.ifPresent(
        a -> {
          if (a.compareTo(this.amount) != 0
              || a.compareTo(BigDecimal.ZERO) < 0
              || a.compareTo(BigDecimal.ZERO) == 0) {
            throw BusinessLogicException.badRequest("transaction amount cannot be changed");
          }
        });
    this.dueDate = dueDate.orElse(this.dueDate);
    this.amount = amount.orElse(this.amount);
    return this;
  }

  public static Transaction restore(
      UUID id,
      UUID accountId,
      UUID organizationId,
      UUID userId,
      OffsetDateTime dateTime,
      LocalDate dueDate,
      String description,
      UUID categoryId,
      BigDecimal amount,
      TransactionStatus status,
      Optional<UUID> parentId,
      Optional<UUID> transferId) {
    var transaction = new Transaction();
    transaction.id = id;
    transaction.accountId = accountId;
    transaction.organizationId = organizationId;
    transaction.userId = userId;
    transaction.dateTime = dateTime;
    transaction.dueDate = dueDate;
    transaction.description = description;
    transaction.categoryId = categoryId;
    transaction.amount = amount;
    transaction.parentId = parentId;
    transaction.status = status;
    transaction.transferId = transferId;

    return transaction;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public UUID getId() {
    return id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public Optional<UUID> getParentId() {
    return parentId;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public UUID getUserId() {
    return userId;
  }

  public Optional<UUID> getTransferId() {
    return transferId;
  }
}
