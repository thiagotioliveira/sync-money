package dev.thiagooliveira.syncmoney.application.transaction.domain.dto;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;

public record CreateDefaultCategoryInput(String name, CategoryType type) {}
