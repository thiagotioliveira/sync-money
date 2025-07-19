package dev.thiagooliveira.syncmoney.application.account.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Account(
    UUID id,
    String name,
    Bank bank,
    UUID organizationId,
    BigDecimal balance,
    OffsetDateTime createdAt) {}
