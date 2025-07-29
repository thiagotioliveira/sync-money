package dev.thiagooliveira.syncmoney.core.transaction.application.service.impl;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryServiceImpl implements CategoryService {

  private final CreateCategory createCategory;
  private final GetCategory getCategory;

  public CategoryServiceImpl(CreateCategory createCategory, GetCategory getCategory) {
    this.createCategory = createCategory;
    this.getCategory = getCategory;
  }

  @Override
  public Category create(CreateCategoryInput input) {
    return this.createCategory.execute(input);
  }

  @Override
  public Category create(CreateDefaultCategoryInput input) {
    return this.createCategory.execute(input);
  }

  @Override
  public Optional<Category> getById(UUID organizationId, UUID id) {
    return this.getCategory.getById(organizationId, id);
  }

  @Override
  public List<Category> getAll(UUID organizationId) {
    return this.getCategory.getAll(organizationId);
  }
}
