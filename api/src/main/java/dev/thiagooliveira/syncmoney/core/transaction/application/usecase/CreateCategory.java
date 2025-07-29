package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.CategoryRepository;

public class CreateCategory {

  private final EventPublisher eventPublisher;
  private final CategoryRepository categoryRepository;

  public CreateCategory(EventPublisher eventPublisher, CategoryRepository categoryRepository) {
    this.eventPublisher = eventPublisher;
    this.categoryRepository = categoryRepository;
  }

  public Category execute(CreateCategoryInput input) {
    if (this.categoryRepository.exists(input)) {
      throw BusinessLogicException.badRequest("category already exists");
    }
    var category = categoryRepository.create(input);
    category.getEvents().forEach(this.eventPublisher::publish);
    return category;
  }

  public Category execute(CreateDefaultCategoryInput input) {
    if (this.categoryRepository.existsDefaultByType(input.type())) {
      throw BusinessLogicException.badRequest("default category already exists");
    }
    var category = this.categoryRepository.create(input);
    category.getEvents().forEach(this.eventPublisher::publish);
    return category;
  }
}
