package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryPort {

  Category save(CreateCategoryInput input);

  Category save(CreateDefaultCategoryInput input);

  boolean existsDefaultByType(CategoryType type);

  boolean exists(CreateCategoryInput input);

  Optional<Category> findById(UUID organizationId, UUID id);

  List<Category> findAll(UUID organizationId);
}
