package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Installment(
    UUID id,
    UUID accountId,
    UUID organizationId,
    UUID parentId,
    LocalDate dueDate,
    String description,
    UUID categoryId,
    BigDecimal amount,
    TransactionStatus status) {}
