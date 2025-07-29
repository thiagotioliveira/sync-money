package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

  Category create(CreateCategoryInput input);

  Category create(CreateDefaultCategoryInput input);

  boolean existsDefaultByType(CategoryType type);

  boolean exists(CreateCategoryInput input);

  Optional<Category> getById(UUID organizationId, UUID id);

  Optional<Category> getDefaultByType(CategoryType type);

  List<Category> getAll(UUID organizationId);
}
