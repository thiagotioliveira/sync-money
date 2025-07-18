package dev.thiagooliveira.syncmoney.infra.category.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.thiagooliveira.syncmoney.TestcontainersConfiguration;
import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class CategoryServiceIT {

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private CategoryService categoryService;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should to create category")
  public void shouldCreateCategory() {
    var org = this.organizationRepository.save(createOrganizationEntity());
    var category = this.categoryService.create(createCreditCategory(org.getId()));
    assertNotNull(category);
    assertNotNull(category.id());
    assertEquals(CATEGORY_CREDIT_NAME, category.name());
    assertEquals(CategoryType.CREDIT, category.type());
    assertEquals(org.getId(), category.organizationId().get());
  }
}
