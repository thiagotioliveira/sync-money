package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetCategory {

  private final CategoryRepository categoryRepository;

  public GetCategory(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Optional<Category> getById(UUID organizationId, UUID id) {
    return this.categoryRepository.getById(organizationId, id);
  }

  public Optional<Category> getDefaultByType(CategoryType type) {
    return this.categoryRepository.getDefaultByType(type);
  }

  public List<Category> getAll(UUID organizationId) {
    return this.categoryRepository.getAll(organizationId);
  }
}
