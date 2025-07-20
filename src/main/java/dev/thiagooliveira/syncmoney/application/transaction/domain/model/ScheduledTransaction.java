package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record ScheduledTransaction(
    UUID id,
    UUID templateId,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    ScheduledTransactionType type,
    ScheduledTransactionStatus status,
    Frequency frequency,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal,
    Optional<UUID> transactionId) {}
