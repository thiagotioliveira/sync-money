package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.PayableReceivableCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.PayableReceivableUpdatedEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

public class PayableReceivable implements AggregateRoot {
  private UUID id;
  private UUID accountId;
  private UUID organizationId;
  private UUID categoryId;
  private String description;
  private BigDecimal amount;
  private LocalDate startDate;
  private LocalDate endDate;
  private boolean recurring;
  private Optional<Integer> installmentTotal;

  public static PayableReceivable restore(
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
    var payableReceivable = new PayableReceivable();
    payableReceivable.id = id;
    payableReceivable.accountId = accountId;
    payableReceivable.organizationId = organizationId;
    payableReceivable.categoryId = categoryId;
    payableReceivable.description = description;
    payableReceivable.amount = amount;
    payableReceivable.startDate = startDate;
    payableReceivable.endDate = endDate;
    payableReceivable.recurring = recurring;
    payableReceivable.installmentTotal = installmentTotal;

    return payableReceivable;
  }

  public PayableReceivable update(Optional<LocalDate> endDate, Optional<BigDecimal> amount) {
    endDate.ifPresent(
        e -> {
          if (e.isBefore(this.startDate)) {
            throw new IllegalArgumentException("end date cannot be before start date");
          }
        });
    amount.ifPresent(
        a -> {
          if (a.compareTo(this.amount) != 0
              || a.compareTo(BigDecimal.ZERO) < 0
              || a.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("amount cannot be changed");
          }
        });
    this.endDate = endDate.orElse(this.endDate);
    this.amount = amount.orElse(this.amount);
    return this;
  }

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
      LocalDate dueDate = this.startDate;

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
    return Installment.restore(
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

  public PayableReceivable created() {
    DomainEventPublisher.addEvent(
        new PayableReceivableCreatedEvent(
            this.getId(),
            this.getOrganizationId(),
            this.getAccountId(),
            this.getStartDate(),
            this.getEndDate(),
            this.isRecurring(),
            OffsetDateTime.now()));
    return this;
  }

  public PayableReceivable updated() {
    DomainEventPublisher.addEvent(
        new PayableReceivableUpdatedEvent(
            this.getId(),
            this.getOrganizationId(),
            this.getAccountId(),
            this.getStartDate(),
            this.getEndDate(),
            this.isRecurring(),
            OffsetDateTime.now()));
    return this;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public UUID getId() {
    return id;
  }

  public Optional<Integer> getInstallmentTotal() {
    return installmentTotal;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public boolean isRecurring() {
    return recurring;
  }

  public LocalDate getStartDate() {
    return startDate;
  }
}
