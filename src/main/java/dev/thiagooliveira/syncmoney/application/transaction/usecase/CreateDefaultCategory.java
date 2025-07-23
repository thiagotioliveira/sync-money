package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.CategoryPort;

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
