package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryServiceImpl implements CategoryService {

  private final EventPublisher eventPublisher;
  private final CreateCategory createCategory;
  private final GetCategory getCategory;

  public CategoryServiceImpl(
      EventPublisher eventPublisher, CreateCategory createCategory, GetCategory getCategory) {
    this.eventPublisher = eventPublisher;
    this.createCategory = createCategory;
    this.getCategory = getCategory;
  }

  @Override
  public Category create(CreateCategoryInput input) {
    Category category = this.createCategory.execute(input);
    category.getEvents().forEach(this.eventPublisher::publish);
    return category;
  }

  @Override
  public Category create(CreateDefaultCategoryInput input) {
    Category category = this.createCategory.execute(input);
    category.getEvents().forEach(this.eventPublisher::publish);
    return category;
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
