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
  public CreateAccount createAccount(
      BankRepository bankRepository, AccountRepository accountRepository) {
    return new CreateAccount(bankRepository, accountRepository);
  }

  @Bean
  public GetAccount getAccount(AccountRepository accountRepository, BankRepository bankRepository) {
    return new GetAccount(accountRepository, bankRepository);
  }

  @Bean
  public UpdateAccountBalance updateAccountBalance(AccountRepository accountRepository) {
    return new UpdateAccountBalance(accountRepository);
  }

  @Bean
  public TransactionEventListener transactionListener(
      EventPublisher eventPublisher, UpdateAccountBalance updateAccountBalance) {
    return new TransactionEventListener(eventPublisher, updateAccountBalance);
  }

  @Bean
  public AccountService accountService(
      EventPublisher eventPublisher, CreateAccount createAccount, GetAccount getAccount) {
    return new AccountServiceImpl(eventPublisher, createAccount, getAccount);
  }
}
