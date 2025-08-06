package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.impl.AccountSummaryServiceImpl;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetAccountSummary;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountSummaryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountSummaryConfiguration {

  @Bean
  public AccountSummaryCalculator accountSummaryCalculator(
      AccountClient accountClient,
      GetTransaction getTransaction,
      CreateAccountSummary createAccountSummary,
      AccountSummaryRepository accountSummaryRepository) {
    return new AccountSummaryCalculator(
        accountClient, getTransaction, createAccountSummary, accountSummaryRepository);
  }

  @Bean
  public CreateAccountSummary createAccountSummary(
      AccountSummaryRepository accountSummaryRepository) {
    return new CreateAccountSummary(accountSummaryRepository);
  }

  @Bean
  public GetAccountSummary getAccountSummary(
      AccountClient accountClient, AccountSummaryCalculator accountSummaryCalculator) {
    return new GetAccountSummary(accountClient, accountSummaryCalculator);
  }

  @Bean
  public AccountSummaryService accountSummaryService(GetAccountSummary getAccountSummary) {
    return new AccountSummaryServiceImpl(getAccountSummary);
  }
}
