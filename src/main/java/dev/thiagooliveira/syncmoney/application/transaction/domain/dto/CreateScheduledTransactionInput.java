package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreateScheduledTransactionInput(
    UUID organizationId,
    UUID accountId,
    UUID categoryId,
    ScheduledTransactionType type,
    String description,
    BigDecimal amount,
    LocalDate startDate,
    boolean recurring,
    Optional<Integer> installmentTotal // recurring = false
    ) {
  public boolean isRecurring() {
    return recurring;
  }

  public boolean isAmountGraterThan(BigDecimal amount) {
    return this.amount.compareTo(amount) < 0;
  }
}
