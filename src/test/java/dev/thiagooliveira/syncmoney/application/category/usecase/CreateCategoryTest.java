package dev.thiagooliveira.syncmoney.application.category.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createCategory;
import static dev.thiagooliveira.syncmoney.util.TestUtil.createCreditCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateCategoryTest {

  private EventPublisher eventPublisher;
  private CategoryPort categoryPort;

  private CreateCategory createCategory;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.categoryPort = mock(CategoryPort.class);
    this.createCategory = new CreateCategory(eventPublisher, categoryPort);
  }

  @Test
  @DisplayName("should save new category")
  void execute() {
    UUID organizationId = UUID.randomUUID();
    CreateCategoryInput input = createCreditCategory(organizationId);
    when(this.categoryPort.exists(eq(input))).thenReturn(false);
    Category categoryExpected = createCategory(organizationId, input);
    when(this.categoryPort.save(eq(input))).thenReturn(categoryExpected);
    var category = this.createCategory.execute(input);
    assertNotNull(category);
    assertEquals(categoryExpected, category);
  }

  @Test
  @DisplayName("should got a bad request because the category already exist")
  void executeWithBadRequest() {
    CreateCategoryInput input = createCreditCategory(UUID.randomUUID());
    when(this.categoryPort.exists(input)).thenReturn(true);
    var exception =
        assertThrows(BusinessLogicException.class, () -> this.createCategory.execute(input));
    assertEquals("category already exists", exception.getMessage());
  }
}
