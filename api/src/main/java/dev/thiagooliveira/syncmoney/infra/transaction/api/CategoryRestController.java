package dev.thiagooliveira.syncmoney.infra.transaction.api;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.transaction.api.mapper.CategoryMapper;
import dev.thiagooliveira.syncmoney.infra.transaction.service.CategoryServiceProxy;
import dev.thiagooliveira.syncmoney.infra.transactions.api.CategoriesApi;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.GetCategoriesResponseBody;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostCategoryRequestBody;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostCategoryResponseBody;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController implements CategoriesApi {

  private final CategoryMapper categoryMapper;
  private final CategoryService categoryService;

  public CategoryRestController(
      CategoryMapper categoryMapper, CategoryServiceProxy categoryService) {
    this.categoryMapper = categoryMapper;
    this.categoryService = categoryService;
  }

  @Override
  public ResponseEntity<PostCategoryResponseBody> createCategory(
      PostCategoryRequestBody postCategoryRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.created(null)
        .body(
            this.categoryMapper.mapToPostCategoryRequestBody(
                this.categoryService.create(
                    this.categoryMapper.mapToCreateCategoryInput(
                        principal.organizationId(), postCategoryRequestBody))));
  }

  @Override
  public ResponseEntity<GetCategoriesResponseBody> getById(UUID id) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.categoryMapper.mapToGetCategoriesResponseBody(
            this.categoryService
                .getById(principal.organizationId(), id)
                .orElseThrow(() -> BusinessLogicException.notFound("category not found"))));
  }

  @Override
  public ResponseEntity<List<GetCategoriesResponseBody>> listCategories() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.categoryService.getAll(principal.organizationId()).stream()
            .map(categoryMapper::mapToGetCategoriesResponseBody)
            .toList());
  }
}
