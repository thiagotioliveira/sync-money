package dev.thiagooliveira.syncmoney.infra.account.config;

import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import dev.thiagooliveira.syncmoney.application.account.domain.port.BankPort;
import dev.thiagooliveira.syncmoney.application.account.usecase.*;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

  @Bean
  public CreateBank createBank(EventPublisher eventPublisher, BankPort bankPort) {
    return new CreateBank(eventPublisher, bankPort);
  }

  @Bean
  public GetBank getBank(BankPort bankPort) {
    return new GetBank(bankPort);
  }

  @Bean
  public CreateAccount createAccount(
      EventPublisher eventPublisher, GetBank getBank, AccountPort accountPort) {
    return new CreateAccount(eventPublisher, getBank, accountPort);
  }

  @Bean
  public GetAccount getAccount(AccountPort accountPort) {
    return new GetAccount(accountPort);
  }

  @Bean
  public UpdateAccountBalance updateAccountBalance(
      EventPublisher eventPublisher, GetAccount getAccount, AccountPort accountPort) {
    return new UpdateAccountBalance(eventPublisher, getAccount, accountPort);
  }
}
