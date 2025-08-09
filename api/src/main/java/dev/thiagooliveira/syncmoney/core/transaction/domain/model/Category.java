package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.CategoryCreatedEvent;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class Category implements AggregateRoot {

  private UUID id;
  private Optional<UUID> organizationId;
  private String name;
  private CategoryType type;

  private Category() {}

  public static Category restore(
      UUID id, Optional<UUID> organizationId, String name, CategoryType type) {
    var category = new Category();
    category.id = id;
    category.organizationId = organizationId;
    category.name = name;
    category.type = type;
    return category;
  }

  public Category created() {
    DomainEventPublisher.addEvent(new CategoryCreatedEvent(this, OffsetDateTime.now()));
    return this;
  }

  public boolean isCredit() {
    return type == CategoryType.CREDIT;
  }

  public boolean isDebit() {
    return type == CategoryType.DEBIT;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Optional<UUID> getOrganizationId() {
    return organizationId;
  }

  public CategoryType getType() {
    return type;
  }
}
