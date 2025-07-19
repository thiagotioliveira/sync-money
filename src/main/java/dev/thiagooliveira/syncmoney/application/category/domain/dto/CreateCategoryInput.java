package dev.thiagooliveira.syncmoney.application.category.domain.dto;

import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import java.util.UUID;

public record CreateCategoryInput(UUID organizationId, String name, CategoryType type) {}
