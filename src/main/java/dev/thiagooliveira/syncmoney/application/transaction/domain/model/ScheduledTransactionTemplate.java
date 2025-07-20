package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ScheduledTransactionTemplate(
    UUID id,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    ScheduledTransactionType type,
    LocalDate startDate,
    LocalDate endDate,
    boolean enabled,
    boolean recurring,
    Frequency frequency,
    Optional<Integer> installmentTotal) {

  public List<ScheduledTransaction> generateInstallments() {
    if (this.recurring) {
      return List.of(createInstallment(this.startDate, this.amount, Optional.empty()));
    } else {
      int totalInstallments = this.installmentTotal.orElse(1);
      BigDecimal totalAmount = this.amount;
      BigDecimal[] division = totalAmount.divideAndRemainder(BigDecimal.valueOf(totalInstallments));
      BigDecimal baseAmount = division[0].setScale(2, RoundingMode.DOWN);
      BigDecimal remainder = division[1];

      List<ScheduledTransaction> items = new ArrayList<>();
      LocalDate dueDate = this.startDate();

      for (int i = 1; i <= totalInstallments; i++) {
        BigDecimal amount = baseAmount;
        if (i == totalInstallments) {
          amount = baseAmount.add(remainder);
        }
        items.add(createInstallment(dueDate, amount, Optional.of(i)));
      }
      return items;
    }
  }

  private ScheduledTransaction createInstallment(
      LocalDate dueDate, BigDecimal amount, Optional<Integer> installmentNumber) {
    return new ScheduledTransaction(
        UUID.randomUUID(),
        this.id,
        this.accountId,
        this.categoryId,
        this.description
            + (this.installmentTotal
                .map(integer -> " (%d/%d)".formatted(installmentNumber.orElse(1), integer))
                .orElse("")),
        amount,
        dueDate,
        this.type,
        ScheduledTransactionStatus.SCHEDULED,
        this.frequency,
        installmentNumber,
        this.installmentTotal,
        Optional.empty());
  }
}
