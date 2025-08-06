package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransferInput(
    UUID organizationId,
    UUID sourceAccountId,
    UUID targetAccountId,
    BigDecimal sourceAmount,
    BigDecimal targetAmount,
    UUID sourceCategoryId,
    UUID targetCategoryId,
    UUID userId) {

  public CreateTransactionInput toCreateDebitTransaction(
      OffsetDateTime dateTime, String description) {
    return new CreateTransactionInput(
        this.sourceAccountId,
        this.organizationId,
        this.userId,
        dateTime,
        description,
        this.sourceCategoryId,
        this.sourceAmount);
  }

  public CreateTransactionInput toCreateCreditTransaction(
      OffsetDateTime dateTime, String description) {
    return new CreateTransactionInput(
        this.targetAccountId,
        this.organizationId,
        this.userId,
        dateTime,
        description,
        this.targetCategoryId,
        this.targetAmount);
  }
}
