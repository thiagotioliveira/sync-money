package dev.thiagooliveira.syncmoney.infra.category.config;

import dev.thiagooliveira.syncmoney.application.category.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.application.category.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.application.category.usecase.CreateDefaultCategory;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.infra.category.persistence.adapter.CategoryAdapter;
import dev.thiagooliveira.syncmoney.infra.category.persistence.repository.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

  @Bean
  public CategoryPort categoryPort(CategoryRepository categoryRepository) {
    return new CategoryAdapter(categoryRepository);
  }

  @Bean
  public CreateCategory createCategory(EventPublisher eventPublisher, CategoryPort categoryPort) {
    return new CreateCategory(eventPublisher, categoryPort);
  }

  @Bean
  public CreateDefaultCategory createDefaultCategory(
      EventPublisher eventPublisher, CategoryPort categoryPort) {
    return new CreateDefaultCategory(eventPublisher, categoryPort);
  }
}
