package dev.thiagooliveira.syncmoney.application.category.usecase;

import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;

public class CreateDefaultCategory {

  public final EventPublisher eventPublisher;
  public final CategoryPort categoryPort;

  public CreateDefaultCategory(EventPublisher eventPublisher, CategoryPort categoryPort) {
    this.eventPublisher = eventPublisher;
    this.categoryPort = categoryPort;
  }

  public Category execute(CreateDefaultCategoryInput input) {
    if (this.categoryPort.existsDefaultByType(input.type())) {
      throw BusinessLogicException.badRequest("default category already exists");
    }
    return this.categoryPort.save(input);
  }
}
