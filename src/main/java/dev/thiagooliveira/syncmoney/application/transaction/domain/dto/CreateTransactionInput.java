package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransactionInput(
    UUID accountId,
    UUID organizationId,
    OffsetDateTime dateTime,
    String description,
    UUID categoryId,
    BigDecimal amount) {}
