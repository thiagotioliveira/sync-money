package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.CategoryEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryRepository;
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
