package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record TransactionEnriched(
    UUID id,
    UUID accountId,
    UUID organizationId,
    UUID userId,
    OffsetDateTime dateTime,
    LocalDate dueDate,
    String description,
    Category category,
    BigDecimal amount,
    TransactionStatus status,
    Optional<UUID> parentId) {
  public TransactionEnriched(Transaction transaction, Category category) {
    this(
        transaction.getId(),
        transaction.getAccountId(),
        transaction.getOrganizationId(),
        transaction.getUserId(),
        transaction.getDateTime(),
        transaction.getDueDate(),
        transaction.getDescription(),
        category,
        transaction.getAmount(),
        transaction.getStatus(),
        transaction.getParentId());
  }

  public boolean isScheduled() {
    return this.status == TransactionStatus.SCHEDULED;
  }

  public boolean isPaid() {
    return this.status == TransactionStatus.PAID;
  }

  public boolean isDebit() {
    return this.category.isDebit();
  }

  public boolean isCredit() {
    return this.category.isCredit();
  }
}
