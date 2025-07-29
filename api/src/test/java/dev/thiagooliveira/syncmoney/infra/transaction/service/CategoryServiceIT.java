package dev.thiagooliveira.syncmoney.infra.transaction.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;

import dev.thiagooliveira.syncmoney.infra.IntegrationTest;

public class CategoryServiceIT extends IntegrationTest {
  /*
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
    assertNotNull(category.id());
    assertEquals(CATEGORY_CREDIT_NAME, category.name());
    assertEquals(CategoryType.CREDIT, category.type());
    assertEquals(org.getId(), category.organizationId().get());
  }*/
}
