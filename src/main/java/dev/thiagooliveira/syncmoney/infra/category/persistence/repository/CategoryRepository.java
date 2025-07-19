package dev.thiagooliveira.syncmoney.infra.category.persistence.repository;

import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.infra.category.persistence.entity.CategoryEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
  boolean existsCategoryEntitiesByTypeAndOrganizationIdIsNull(CategoryType type);

  boolean existsCategoryEntitiesByNameIgnoreCaseAndTypeAndOrganizationId(
      String name, CategoryType type, UUID organizationId);

  @Query(
      """
      SELECT c FROM CategoryEntity c
      WHERE c.id = :id AND (c.organizationId = :organizationId OR c.organizationId IS NULL)
    """)
  Optional<CategoryEntity> findByIdAndOrganizationIdOrOrganizationIdIsNull(
      @Param("id") UUID id, @Param("organizationId") UUID organizationId);
}
