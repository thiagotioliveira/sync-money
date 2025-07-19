package dev.thiagooliveira.syncmoney.application.category.domain.port;

import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPort {

  Category save(CreateCategoryInput input);

  Category save(CreateDefaultCategoryInput input);

  boolean existsDefaultByType(CategoryType type);

  boolean exists(CreateCategoryInput input);

  Optional<Category> findById(UUID organizationId, UUID id);
}
