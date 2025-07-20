package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Frequency;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionStatus;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionType;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record ScheduledTransactionEnriched(
    UUID id,
    UUID templateId,
    UUID accountId,
    Category category,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    ScheduledTransactionType type,
    ScheduledTransactionStatus status,
    Frequency frequency,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal,
    Optional<UUID> transactionId) {
  public ScheduledTransactionEnriched(
      String id,
      String templateId,
      String accountId,
      String categoryId,
      String categoryName,
      String categoryType,
      String description,
      BigDecimal amount,
      Date dueDate,
      String type,
      String status,
      String frequency,
      Integer installmentNumber,
      Integer installmentTotal,
      String transactionId) {
    this(
        UUID.fromString(id),
        UUID.fromString(templateId),
        UUID.fromString(accountId),
        new Category(UUID.fromString(categoryId), categoryName, categoryType),
        description,
        amount,
        dueDate.toLocalDate(),
        ScheduledTransactionType.valueOf(type),
        ScheduledTransactionStatus.valueOf(status),
        Frequency.valueOf(frequency),
        Optional.ofNullable(installmentNumber),
        Optional.ofNullable(installmentTotal),
        Optional.ofNullable(transactionId != null ? UUID.fromString(transactionId) : null));
  }

  public static class Category {
    private final UUID id;
    private final String name;
    private final String type;

    public Category(UUID id, String name, String type) {
      this.id = id;
      this.name = name;
      this.type = type;
    }

    public UUID getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }
  }
}
