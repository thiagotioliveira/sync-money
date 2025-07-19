package dev.thiagooliveira.syncmoney.application.category.domain.dto;

import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;

public record CreateDefaultCategoryInput(String name, CategoryType type) {}
