package dev.thiagooliveira.syncmoney.core.user.application.dto;

import java.util.UUID;

public record CreateUserInOrganizationInput(
    String email, String name, String password, UUID organizationId) {}
