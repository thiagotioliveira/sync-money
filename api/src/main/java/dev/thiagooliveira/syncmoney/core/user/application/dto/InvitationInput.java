package dev.thiagooliveira.syncmoney.core.user.application.dto;

import java.util.UUID;

public record InvitationInput(UUID invitedBy, String email, UUID organizationId) {}
