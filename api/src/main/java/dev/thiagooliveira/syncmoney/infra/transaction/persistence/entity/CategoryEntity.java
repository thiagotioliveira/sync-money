package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.event.CategoryCreatedEvent;
import jakarta.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {
  @Id private UUID id;

  @Column private UUID organizationId;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoryType type;

  public CategoryEntity() {}

  public static CategoryEntity create(CreateCategoryInput input) {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.id = UUID.randomUUID();
    categoryEntity.organizationId = input.organizationId();
    categoryEntity.name = input.name();
    categoryEntity.type = input.type();
    return categoryEntity;
  }

  public static CategoryEntity create(CreateDefaultCategoryInput input) {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.id = UUID.randomUUID();
    categoryEntity.organizationId = null;
    categoryEntity.name = input.name();
    categoryEntity.type = input.type();
    return categoryEntity;
  }

  public static Category create(CategoryEntity categoryEntity) {
    return Category.restore(
        categoryEntity.getId(),
        Optional.ofNullable(categoryEntity.getOrganizationId()),
        categoryEntity.getName(),
        categoryEntity.getType());
  }

  public Category toCategory() {
    return Category.restore(id, Optional.ofNullable(organizationId), name, type);
  }

  public Category toCategoryCreated() {
    var category = toCategory();
    category.registerEvent(new CategoryCreatedEvent(category));
    return category;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CategoryType getType() {
    return type;
  }

  public void setType(CategoryType type) {
    this.type = type;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
