package dev.thiagooliveira.syncmoney.application.category.usecase;

import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.dto.event.CategoryCreatedEvent;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;

public class CreateCategory {

  private final EventPublisher eventPublisher;
  private final CategoryPort categoryPort;

  public CreateCategory(EventPublisher eventPublisher, CategoryPort categoryPort) {
    this.eventPublisher = eventPublisher;
    this.categoryPort = categoryPort;
  }

  public Category execute(CreateCategoryInput input) {
    if (this.categoryPort.exists(input)) {
      throw BusinessLogicException.badRequest("category already exists");
    }
    var category = categoryPort.save(input);
    this.eventPublisher.publish(new CategoryCreatedEvent(category));
    return category;
  }
}
