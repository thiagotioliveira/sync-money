package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;

public record CreateDefaultCategoryInput(String name, CategoryType type) {}
