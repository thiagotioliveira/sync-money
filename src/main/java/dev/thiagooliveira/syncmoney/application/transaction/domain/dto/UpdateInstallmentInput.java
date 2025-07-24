package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateInstallmentInput(LocalDate dueDate, BigDecimal amount) {

  public UpdateInstallmentInput(LocalDate dueDate, BigDecimal amount) {
    this.dueDate = dueDate;
    this.amount = amount;
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    }
    if (dueDate.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("due date must be in the future");
    }
  }
}
