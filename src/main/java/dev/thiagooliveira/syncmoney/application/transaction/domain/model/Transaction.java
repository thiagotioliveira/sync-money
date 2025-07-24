package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record Transaction(
    UUID id,
    UUID accountId,
    UUID organizationId,
    OffsetDateTime dateTime,
    LocalDate dueDate,
    String description,
    Category category,
    BigDecimal amount,
    TransactionStatus status,
    Optional<UUID> parentId) {

  public Installment toInstallment() {
    return new Installment(
        this.id,
        this.accountId,
        this.organizationId,
        this.parentId.get(),
        this.dueDate,
        this.description,
        this.category.id(),
        this.amount,
        this.status);
  }
}
