package dev.thiagooliveira.syncmoney.application.category.usecase;

import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import java.util.Optional;
import java.util.UUID;

public class GetCategory {

  private final CategoryPort categoryPort;

  public GetCategory(CategoryPort categoryPort) {
    this.categoryPort = categoryPort;
  }

  public Optional<Category> findById(UUID organizationId, UUID id) {
    return this.categoryPort.findById(organizationId, id);
  }
}
