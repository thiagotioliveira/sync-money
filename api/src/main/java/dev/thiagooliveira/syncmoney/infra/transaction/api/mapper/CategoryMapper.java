package dev.thiagooliveira.syncmoney.infra.transaction.api.mapper;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.GetCategoriesResponseBody;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostCategoryRequestBody;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostCategoryResponseBody;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  GetCategoriesResponseBody mapToGetCategoriesResponseBody(Category category);

  CreateCategoryInput mapToCreateCategoryInput(
      UUID organizationId, PostCategoryRequestBody postCategoryRequestBody);

  PostCategoryResponseBody mapToPostCategoryRequestBody(Category category);
}
