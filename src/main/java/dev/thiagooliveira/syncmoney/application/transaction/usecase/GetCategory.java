package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.CategoryPort;
import java.util.List;
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

  public List<Category> findAll(UUID organizationId) {
    return this.categoryPort.findAll(organizationId);
  }
}
