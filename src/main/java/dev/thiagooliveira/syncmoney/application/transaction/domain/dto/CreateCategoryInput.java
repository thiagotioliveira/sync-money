package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;
import java.util.UUID;

public record CreateCategoryInput(UUID organizationId, String name, CategoryType type) {}
