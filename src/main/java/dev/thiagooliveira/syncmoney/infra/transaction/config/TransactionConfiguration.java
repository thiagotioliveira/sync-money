package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetCategory;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfiguration {

  @Bean
  public CreateTransaction createTransaction(
      EventPublisher eventPublisher,
      GetAccount getAccount,
      GetCategory getCategory,
      TransactionPort transactionPort) {
    return new CreateTransaction(eventPublisher, getAccount, getCategory, transactionPort);
  }

  @Bean
  public CreateScheduledTransaction createScheduledTransaction(
      EventPublisher eventPublisher,
      GetAccount getAccount,
      GetCategory getCategory,
      ScheduledTransactionPort scheduledTransactionPort) {
    return new CreateScheduledTransaction(
        eventPublisher, getAccount, getCategory, scheduledTransactionPort);
  }

  @Bean
  public GetTransaction getTransaction(TransactionPort transactionPort) {
    return new GetTransaction(transactionPort);
  }

  @Bean
  public GetScheduledTransaction getScheduledTransaction(
      CreateScheduledTransaction createScheduledTransaction,
      ScheduledTransactionPort scheduledTransactionPort) {
    return new GetScheduledTransaction(createScheduledTransaction, scheduledTransactionPort);
  }
}
