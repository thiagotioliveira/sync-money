package dev.thiagooliveira.syncmoney.infra.transaction.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceIT extends IntegrationTest {
  @Autowired private OrganizationJpaRepository organizationJpaRepository;

  @Autowired private CategoryServiceProxy categoryService;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should to create category")
  public void shouldCreateCategory() {
    var org = this.organizationJpaRepository.save(createOrganizationEntity());
    var category = this.categoryService.create(createCreditCategory(org.getId()));
    assertNotNull(category);
    assertNotNull(category.getId());
    assertEquals(CATEGORY_CREDIT_NAME, category.getName());
    assertEquals(CategoryType.CREDIT, category.getType());
    assertEquals(org.getId(), category.getOrganizationId().get());
  }
}
