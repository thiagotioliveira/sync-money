package dev.thiagooliveira.syncmoney.infra.category.persistence.repository;

import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.infra.category.persistence.entity.CategoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
  boolean existsCategoryEntitiesByTypeAndOrganizationIdIsNull(CategoryType type);

  boolean existsCategoryEntitiesByNameIgnoreCaseAndTypeAndOrganizationId(
      String name, CategoryType type, UUID organizationId);
}
