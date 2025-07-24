package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateCategory;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CreateCategory createCategory;

  public CategoryService(CreateCategory createCategory) {
    this.createCategory = createCategory;
  }

  public Category create(CreateCategoryInput input) {
    return this.createCategory.execute(input);
  }

  public Category create(CreateDefaultCategoryInput input) {
    return this.createCategory.execute(input);
  }
}
