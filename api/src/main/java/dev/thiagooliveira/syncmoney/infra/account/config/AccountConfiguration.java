package dev.thiagooliveira.syncmoney.infra.account.config;

import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountServiceImpl;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.*;
import dev.thiagooliveira.syncmoney.core.account.domain.port.income.TransactionEventListener;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
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
  public TransactionEventListener transactionListener(AccountService accountService) {
    return new TransactionEventListener(accountService);
  }

  @Bean
  public AccountService accountService(
      DomainEventContext domainEventContext,
      CreateAccount createAccount,
      GetAccount getAccount,
      UpdateAccountBalance updateAccountBalance) {
    return new AccountServiceImpl(
        domainEventContext, createAccount, getAccount, updateAccountBalance);
  }
}
