package dev.thiagooliveira.syncmoney.infra.account.config;

import dev.thiagooliveira.syncmoney.core.account.application.service.BankService;
import dev.thiagooliveira.syncmoney.core.account.application.service.BankServiceImpl;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetBank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {

  @Bean
  public CreateBank createBank(BankRepository bankRepository) {
    return new CreateBank(bankRepository);
  }

  @Bean
  public BankService bankService(
      DomainEventContext domainEventContext, CreateBank createBank, GetBank getBank) {
    return new BankServiceImpl(domainEventContext, createBank, getBank);
  }

  @Bean
  public GetBank getBank(BankRepository bankRepository) {
    return new GetBank(bankRepository);
  }
}
