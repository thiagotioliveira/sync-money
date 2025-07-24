package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreatePayableReceivableInput(
    UUID organizationId,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    LocalDate startDate,
    boolean recurring,
    Optional<Integer> installmentTotal) {
  public CreatePayableReceivableInput(
      UUID organizationId,
      UUID accountId,
      UUID categoryId,
      String description,
      BigDecimal amount,
      LocalDate startDate,
      boolean recurring,
      Optional<Integer> installmentTotal) {
    this.organizationId = organizationId;
    this.accountId = accountId;
    this.categoryId = categoryId;
    this.description = description;
    this.amount = amount;
    this.startDate = startDate;
    this.recurring = recurring;
    this.installmentTotal = installmentTotal != null ? installmentTotal : Optional.empty();

    validate();
  }

  private void validate() {
    if (startDate == null || LocalDate.now().isAfter(startDate)) {
      throw BusinessLogicException.badRequest("start date needs to be today or in the future");
    }

    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw BusinessLogicException.badRequest("amount must be greater than zero");
    }

    if (!recurring) {
      if (installmentTotal.isEmpty() || installmentTotal.get() <= 0) {
        throw BusinessLogicException.badRequest(
            "installment total must be present and greater than zero for non-recurring transactions");
      }
    } else {
      if (installmentTotal.isPresent()) {
        throw BusinessLogicException.badRequest(
            "installment total must be empty for recurring transactions");
      }
    }
  }

  public boolean isAmountGraterThan(BigDecimal value) {
    return this.amount.compareTo(value) > 0;
  }
}
