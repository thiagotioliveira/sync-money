package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.CategoryPort;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateDefaultCategory;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter.CategoryAdapter;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryRepository;
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

  @Bean
  public GetCategory getCategory(CategoryPort categoryPort) {
    return new GetCategory(categoryPort);
  }
}
