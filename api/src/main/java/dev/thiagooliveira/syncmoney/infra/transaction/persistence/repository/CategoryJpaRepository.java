package dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.CategoryEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {

  List<CategoryEntity> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId);

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

  Optional<CategoryEntity> findByOrganizationIdIsNullAndType(CategoryType type);
}
