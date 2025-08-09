package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CategoryServiceProxy implements CategoryService {

  private final CategoryService categoryService;

  public CategoryServiceProxy(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public Category create(CreateCategoryInput input) {
    return this.categoryService.create(input);
  }

  @Override
  public Category create(CreateDefaultCategoryInput input) {
    return this.categoryService.create(input);
  }

  @Override
  public Optional<Category> getById(UUID organizationId, UUID id) {
    return this.categoryService.getById(organizationId, id);
  }

  @Override
  public Optional<Category> getDefaultByType(CategoryType type) {
    return this.categoryService.getDefaultByType(type);
  }

  @Override
  public List<Category> getAll(UUID organizationId) {
    return this.categoryService.getAll(organizationId);
  }
}
