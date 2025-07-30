package dev.thiagooliveira.syncmoney.infra.account.config;

import dev.thiagooliveira.syncmoney.core.account.application.service.BankService;
import dev.thiagooliveira.syncmoney.core.account.application.service.BankServiceImpl;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {

  @Bean
  public CreateBank createBank(EventPublisher eventPublisher, BankRepository bankRepository) {
    return new CreateBank(eventPublisher, bankRepository);
  }

  @Bean
  public BankService bankService(CreateBank createBank) {
    return new BankServiceImpl(createBank);
  }
}
