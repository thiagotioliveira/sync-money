package dev.thiagooliveira.syncmoney.application.category.dto;

import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;

public record CreateDefaultCategoryInput(String name, CategoryType type) {}
