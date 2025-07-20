package dev.thiagooliveira.syncmoney.infra.category.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.infra.category.persistence.entity.CategoryEntity;
import dev.thiagooliveira.syncmoney.infra.category.persistence.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

  @Override
  public Optional<Category> findById(UUID organizationId, UUID id) {
    return this.categoryRepository
        .findByIdAndOrganizationIdOrOrganizationIdIsNull(id, organizationId)
        .map(CategoryEntity::toCategory);
  }

  @Override
  public List<Category> findAll(UUID organizationId) {
    return this.categoryRepository.findByOrganizationId(organizationId).stream()
        .map(CategoryEntity::toCategory)
        .toList();
  }
}
