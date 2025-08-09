package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionScheduledCreatedEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Installment {
  private UUID id;
  private UUID accountId;
  private UUID organizationId;
  private UUID parentId;
  private LocalDate dueDate;
  private String description;
  private UUID categoryId;
  private BigDecimal amount;
  private TransactionStatus status;

  public static Installment restore(
      UUID id,
      UUID accountId,
      UUID organizationId,
      UUID parentId,
      LocalDate dueDate,
      String description,
      UUID categoryId,
      BigDecimal amount,
      TransactionStatus status) {
    var installment = new Installment();
    installment.id = id;
    installment.accountId = accountId;
    installment.organizationId = organizationId;
    installment.parentId = parentId;
    installment.dueDate = dueDate;
    installment.description = description;
    installment.categoryId = categoryId;
    installment.amount = amount;
    installment.status = status;
    return installment;
  }

  public Installment created() {
    DomainEventPublisher.addEvent(
        new TransactionScheduledCreatedEvent(
            this.getId(),
            this.getOrganizationId(),
            this.getAccountId(),
            this.getCategoryId(),
            this.getDueDate(),
            this.getAmount(),
            OffsetDateTime.now()));
    return this;
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

  public UUID getParentId() {
    return parentId;
  }

  public TransactionStatus getStatus() {
    return status;
  }
}
