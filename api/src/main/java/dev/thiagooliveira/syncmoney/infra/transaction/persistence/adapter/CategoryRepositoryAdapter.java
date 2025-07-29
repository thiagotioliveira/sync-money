package dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.CategoryRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.CategoryEntity;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryRepositoryAdapter implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  public CategoryRepositoryAdapter(CategoryJpaRepository categoryJpaRepository) {
    this.categoryJpaRepository = categoryJpaRepository;
  }

  @Override
  public Category create(CreateCategoryInput input) {
    return this.categoryJpaRepository.save(CategoryEntity.create(input)).toCategoryCreated();
  }

  @Override
  public Category create(CreateDefaultCategoryInput input) {
    return this.categoryJpaRepository.save(CategoryEntity.create(input)).toCategoryCreated();
  }

  @Override
  public boolean existsDefaultByType(CategoryType type) {
    return this.categoryJpaRepository.existsCategoryEntitiesByTypeAndOrganizationIdIsNull(type);
  }

  @Override
  public boolean exists(CreateCategoryInput input) {
    return this.categoryJpaRepository
        .existsCategoryEntitiesByNameIgnoreCaseAndTypeAndOrganizationId(
            input.name(), input.type(), input.organizationId());
  }

  @Override
  public Optional<Category> getById(UUID organizationId, UUID id) {
    return this.categoryJpaRepository
        .findByIdAndOrganizationIdOrOrganizationIdIsNull(id, organizationId)
        .map(CategoryEntity::toCategory);
  }

  @Override
  public Optional<Category> getDefaultByType(CategoryType type) {
    return this.categoryJpaRepository
        .findByOrganizationIdIsNullAndType(type)
        .map(CategoryEntity::toCategory);
  }

  @Override
  public List<Category> getAll(UUID organizationId) {
    return this.categoryJpaRepository
        .findByOrganizationIdOrOrganizationIdIsNull(organizationId)
        .stream()
        .map(CategoryEntity::toCategory)
        .toList();
  }
}
