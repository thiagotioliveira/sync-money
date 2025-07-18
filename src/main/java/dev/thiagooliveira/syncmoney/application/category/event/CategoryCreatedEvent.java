package dev.thiagooliveira.syncmoney.application.category.event;

import dev.thiagooliveira.syncmoney.application.category.domain.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.application.event.dto.Event;
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
