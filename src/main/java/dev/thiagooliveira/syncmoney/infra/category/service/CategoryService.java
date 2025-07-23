package dev.thiagooliveira.syncmoney.infra.category.service;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateDefaultCategory;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CreateCategory createCategory;
  private final CreateDefaultCategory createDefaultCategory;

  public CategoryService(
      CreateCategory createCategory, CreateDefaultCategory createDefaultCategory) {
    this.createCategory = createCategory;
    this.createDefaultCategory = createDefaultCategory;
  }

  public Category create(CreateCategoryInput input) {
    return this.createCategory.execute(input);
  }

  public Category create(CreateDefaultCategoryInput input) {
    return this.createDefaultCategory.execute(input);
  }
}
