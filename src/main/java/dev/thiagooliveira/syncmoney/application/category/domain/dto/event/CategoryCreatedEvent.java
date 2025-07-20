package dev.thiagooliveira.syncmoney.application.category.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import java.util.Optional;
import java.util.UUID;

public class CategoryCreatedEvent implements Event {
  private final UUID id;
  private final Optional<UUID> organizationId;
  private final String name;
  private final CategoryType type;

  public CategoryCreatedEvent(Category category) {
    this.id = category.id();
    this.organizationId = category.organizationId();
    this.name = category.name();
    this.type = category.type();
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
