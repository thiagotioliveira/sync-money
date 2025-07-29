package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;

public class CategoryCreatedEvent implements Event {
  private final UUID id;
  private final Optional<UUID> organizationId;
  private final String name;
  private final CategoryType type;

  public CategoryCreatedEvent(Category category) {
    this.id = category.getId();
    this.organizationId = category.getOrganizationId();
    this.name = category.getName();
    this.type = category.getType();
  }

  public UUID getId() {
    return id;
  }

  public Optional<UUID> getOrganizationId() {
    return organizationId;
  }

  public String getName() {
    return name;
  }

  public CategoryType getType() {
    return type;
  }
}
