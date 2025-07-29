package dev.thiagooliveira.syncmoney.infra.security.service;

import java.util.UUID;

public record UserAuthenticated(UUID id, UUID organizationId, String name, String email) {}
