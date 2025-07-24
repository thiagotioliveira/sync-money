package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.PayableReceivablePort;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetCategory;
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
      TransactionPort transactionPort,
      PayableReceivablePort payableReceivablePort) {
    return new CreateTransaction(
        eventPublisher, getAccount, getCategory, transactionPort, payableReceivablePort);
  }

  @Bean
  public GetTransaction getTransaction(
      GetAccount getAccount,
      PayableReceivablePort payableReceivablePort,
      TransactionPort transactionPort) {
    return new GetTransaction(getAccount, payableReceivablePort, transactionPort);
  }
}
