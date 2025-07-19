package dev.thiagooliveira.syncmoney.application.account.domain.dto;

import java.util.UUID;

public record CreateAccountInput(String name, UUID bankId, UUID organizationId) {}
