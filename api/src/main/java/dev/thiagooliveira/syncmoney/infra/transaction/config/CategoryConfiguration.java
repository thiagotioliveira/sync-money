package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryServiceImpl;
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
  public CreateCategory createCategory(CategoryRepository categoryRepository) {
    return new CreateCategory(categoryRepository);
  }

  @Bean
  public GetCategory getCategory(CategoryRepository categoryRepository) {
    return new GetCategory(categoryRepository);
  }

  @Bean
  public CategoryService categoryService(
      DomainEventContext domainEventContext,
      CreateCategory createCategory,
      GetCategory getCategory) {
    return new CategoryServiceImpl(domainEventContext, createCategory, getCategory);
  }
}
