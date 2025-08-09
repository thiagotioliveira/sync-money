package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateDefaultCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

  Category create(CreateCategoryInput input);

  Category create(CreateDefaultCategoryInput input);

  Optional<Category> getById(UUID organizationId, UUID id);

  Optional<Category> getDefaultByType(CategoryType type);

  List<Category> getAll(UUID organizationId);
}
