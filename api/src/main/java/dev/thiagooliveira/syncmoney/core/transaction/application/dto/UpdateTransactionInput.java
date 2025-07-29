package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public record UpdateTransactionInput(
    Optional<LocalDate> dueDate, Optional<BigDecimal> amount, boolean applyNextInstallments) {

  public UpdateTransactionInput(
      Optional<LocalDate> dueDate, Optional<BigDecimal> amount, boolean applyNextInstallments) {
    this.dueDate = dueDate;
    this.amount = amount;
    this.applyNextInstallments = applyNextInstallments;

    if (amount.isPresent() && amount.get().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    }
    if (dueDate.isPresent() && dueDate.get().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("due date must be in the future");
    }
  }
}
