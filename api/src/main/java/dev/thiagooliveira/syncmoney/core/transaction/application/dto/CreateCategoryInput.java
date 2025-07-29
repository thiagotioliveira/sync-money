package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import java.util.UUID;

public record CreateCategoryInput(UUID organizationId, String name, CategoryType type) {}
