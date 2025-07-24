package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransactionInput(
    UUID accountId,
    UUID organizationId,
    OffsetDateTime dateTime,
    String description,
    UUID categoryId,
    BigDecimal amount) {

  public CreateTransactionInput(
      UUID accountId,
      UUID organizationId,
      OffsetDateTime dateTime,
      String description,
      UUID categoryId,
      BigDecimal amount) {
    this.accountId = accountId;
    this.organizationId = organizationId;
    this.dateTime = dateTime;
    this.description = description;
    this.categoryId = categoryId;
    this.amount = amount;
    validate();
  }

  private void validate() {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw BusinessLogicException.badRequest("amount must be greater than zero");
    }
    if (description == null || description.isBlank()) {
      throw BusinessLogicException.badRequest("description must be provided");
    }
    if (OffsetDateTime.now().isBefore(dateTime)) {
      throw BusinessLogicException.badRequest("date must be in the today or in the pass");
    }
  }
}
