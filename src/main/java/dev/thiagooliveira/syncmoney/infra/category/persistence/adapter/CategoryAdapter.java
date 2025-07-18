package dev.thiagooliveira.syncmoney.infra.category.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.category.domain.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.application.category.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.port.CategoryPort;
import dev.thiagooliveira.syncmoney.infra.category.persistence.entity.CategoryEntity;
import dev.thiagooliveira.syncmoney.infra.category.persistence.repository.CategoryRepository;

public class CategoryAdapter implements CategoryPort {

  private final CategoryRepository categoryRepository;

  public CategoryAdapter(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category save(CreateCategoryInput input) {
    return this.categoryRepository.save(CategoryEntity.from(input)).toCategory();
  }

  @Override
  public Category save(CreateDefaultCategoryInput input) {
    return this.categoryRepository.save(CategoryEntity.from(input)).toCategory();
  }

  @Override
  public boolean existsDefaultByType(CategoryType type) {
    return this.categoryRepository.existsCategoryEntitiesByTypeAndOrganizationIdIsNull(type);
  }

  @Override
  public boolean exists(CreateCategoryInput input) {
    return this.categoryRepository.existsCategoryEntitiesByNameIgnoreCaseAndTypeAndOrganizationId(
        input.name(), input.type(), input.organizationId());
  }
}
