package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.UpdateInstallmentInput;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Installment(
    UUID id,
    UUID accountId,
    UUID organizationId,
    UUID parentId,
    LocalDate dueDate,
    String description,
    UUID categoryId,
    BigDecimal amount,
    TransactionStatus status) {

  public Installment update(UpdateInstallmentInput input) {
    return new Installment(
        this.id,
        this.accountId,
        this.organizationId,
        this.parentId,
        input.dueDate(),
        this.description,
        this.categoryId,
        input.amount(),
        this.status);
  }
}
