package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public record PayTransactionInput(
    UUID organizationId,
    UUID accountId,
    UUID userId,
    UUID transactionId,
    OffsetDateTime dateTime,
    Optional<BigDecimal> amount) {}
