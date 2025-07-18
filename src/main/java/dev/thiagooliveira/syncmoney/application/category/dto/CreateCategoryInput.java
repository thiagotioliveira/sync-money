package dev.thiagooliveira.syncmoney.application.category.dto;

import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import java.util.UUID;

public record CreateCategoryInput(UUID organizationId, String name, CategoryType type) {}
