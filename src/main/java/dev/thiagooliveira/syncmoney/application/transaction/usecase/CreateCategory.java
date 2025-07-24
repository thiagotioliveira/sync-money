package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.CategoryCreatedEvent;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.CategoryPort;

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

  public Category execute(CreateDefaultCategoryInput input) {
    if (this.categoryPort.existsDefaultByType(input.type())) {
      throw BusinessLogicException.badRequest("default category already exists");
    }
    return this.categoryPort.save(input);
  }
}
