package dev.thiagooliveira.syncmoney.application.category.port;

import dev.thiagooliveira.syncmoney.application.category.domain.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.application.category.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.dto.CreateDefaultCategoryInput;

public interface CategoryPort {

  Category save(CreateCategoryInput input);

  Category save(CreateDefaultCategoryInput input);

  boolean existsDefaultByType(CategoryType type);

  boolean exists(CreateCategoryInput input);
}
