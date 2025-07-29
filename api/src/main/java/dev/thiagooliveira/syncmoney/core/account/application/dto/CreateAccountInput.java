package dev.thiagooliveira.syncmoney.core.account.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountInput(
    String name, UUID bankId, UUID organizationId, UUID userId, BigDecimal initialBalance) {}
