package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

public record PayableReceivable(
    UUID id,
    UUID accountId,
    UUID organizationId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    LocalDate startDate,
    LocalDate endDate,
    boolean recurring,
    Optional<Integer> installmentTotal) {

  public Installment generateInstallment(LocalDate dueDate) {
    return createInstallment(dueDate, this.amount, Optional.empty());
  }

  public List<Installment> generateInstallments() {
    if (this.recurring) {
      return List.of(createInstallment(this.startDate, this.amount, Optional.empty()));
    } else {
      int totalInstallments = this.installmentTotal.orElse(1);
      BigDecimal totalAmount = this.amount;
      BigDecimal[] division = totalAmount.divideAndRemainder(BigDecimal.valueOf(totalInstallments));
      BigDecimal baseAmount = division[0].setScale(2, RoundingMode.DOWN);
      BigDecimal remainder = division[1];

      List<Installment> installments = new ArrayList<>();
      LocalDate dueDate = this.startDate();

      for (int i = 1; i <= totalInstallments; i++) {
        BigDecimal amount = baseAmount;
        if (i == totalInstallments) {
          amount = baseAmount.add(remainder);
        }
        installments.add(createInstallment(dueDate, amount, Optional.of(i)));
      }
      return Collections.unmodifiableList(installments);
    }
  }

  private Installment createInstallment(
      LocalDate dueDate, BigDecimal amount, Optional<Integer> installmentNumber) {
    return new Installment(
        UUID.randomUUID(),
        this.accountId,
        this.organizationId,
        this.id,
        dueDate,
        this.description
            + (this.installmentTotal
                .map(integer -> " (%d/%d)".formatted(installmentNumber.orElse(1), integer))
                .orElse("")),
        this.categoryId,
        amount,
        TransactionStatus.SCHEDULED);
  }
}
