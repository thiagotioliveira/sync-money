package dev.thiagooliveira.syncmoney.application.account.domain.model;

import java.util.UUID;

public record Bank(UUID id, UUID organizationId, String name, Currency currency) {}
