package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.impl.CategoryServiceImpl;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateCategory;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.CategoryRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.adapter.CategoryRepositoryAdapter;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

  @Bean
  public CategoryRepository categoryPort(CategoryJpaRepository categoryJpaRepository) {
    return new CategoryRepositoryAdapter(categoryJpaRepository);
  }

  @Bean
  public CreateCategory createCategory(
      EventPublisher eventPublisher, CategoryRepository categoryRepository) {
    return new CreateCategory(eventPublisher, categoryRepository);
  }

  @Bean
  public GetCategory getCategory(CategoryRepository categoryRepository) {
    return new GetCategory(categoryRepository);
  }

  @Bean
  public CategoryService categoryService(CreateCategory createCategory, GetCategory getCategory) {
    return new CategoryServiceImpl(createCategory, getCategory);
  }
}
