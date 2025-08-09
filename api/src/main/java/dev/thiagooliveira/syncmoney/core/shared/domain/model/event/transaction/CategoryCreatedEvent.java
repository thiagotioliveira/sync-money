package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class CategoryCreatedEvent implements DomainEvent {
  private final UUID id;
  private final Optional<UUID> organizationId;
  private final String name;
  private final CategoryType type;
  private final OffsetDateTime dateTime;

  public CategoryCreatedEvent(Category category, OffsetDateTime dateTime) {
    this.id = category.getId();
    this.organizationId = category.getOrganizationId();
    this.name = category.getName();
    this.type = category.getType();
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return "CategoryCreatedEvent{"
        + "id="
        + id
        + ", organizationId="
        + organizationId
        + ", name='"
        + name
        + '\''
        + ", type="
        + type
        + ", dateTime="
        + dateTime
        + '}';
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

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
