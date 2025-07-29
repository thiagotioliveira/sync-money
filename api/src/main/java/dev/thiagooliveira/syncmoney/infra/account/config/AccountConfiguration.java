package dev.thiagooliveira.syncmoney.infra.account.config;

import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountServiceImpl;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.*;
import dev.thiagooliveira.syncmoney.core.account.domain.port.income.TransactionEventListener;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

  @Bean
  public CreateBank createBank(EventPublisher eventPublisher, BankRepository bankRepository) {
    return new CreateBank(eventPublisher, bankRepository);
  }

  @Bean
  public CreateAccount createAccount(
      EventPublisher eventPublisher,
      BankRepository bankRepository,
      AccountRepository accountRepository) {
    return new CreateAccount(eventPublisher, bankRepository, accountRepository);
  }

  @Bean
  public GetAccount getAccount(AccountRepository accountRepository, BankRepository bankRepository) {
    return new GetAccount(accountRepository, bankRepository);
  }

  @Bean
  public UpdateAccountBalance updateAccountBalance(
      EventPublisher eventPublisher, AccountRepository accountRepository) {
    return new UpdateAccountBalance(eventPublisher, accountRepository);
  }

  @Bean
  public TransactionEventListener transactionListener(UpdateAccountBalance updateAccountBalance) {
    return new TransactionEventListener(updateAccountBalance);
  }

  @Bean
  public AccountService accountService(
      CreateBank createBank, CreateAccount createAccount, GetAccount getAccount) {
    return new AccountServiceImpl(createBank, createAccount, getAccount);
  }
}
