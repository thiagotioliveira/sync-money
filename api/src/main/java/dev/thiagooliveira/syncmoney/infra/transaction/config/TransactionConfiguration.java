package dev.thiagooliveira.syncmoney.infra.transaction.config;

import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.impl.TransactionServiceImpl;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummaryCalculator;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.income.AccountEventListener;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfiguration {

  @Bean
  public CreateTransaction createTransaction(
      EventPublisher eventPublisher,
      AccountClient accountClient,
      GetCategory getCategory,
      TransactionRepository transactionRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    return new CreateTransaction(
        eventPublisher,
        accountClient,
        getCategory,
        transactionRepository,
        accountSummaryCalculator);
  }

  @Bean
  public CreatePayableReceivable createPayableReceivable(
      EventPublisher eventPublisher,
      GetAccount getAccount,
      GetCategory getCategory,
      TransactionRepository transactionRepository,
      PayableReceivableRepository payableReceivableRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    return new CreatePayableReceivable(
        eventPublisher,
        getAccount,
        getCategory,
        transactionRepository,
        payableReceivableRepository,
        accountSummaryCalculator);
  }

  @Bean
  public GetTransaction getTransaction(
      AccountClient accountClient,
      GetCategory getCategory,
      PayableReceivableRepository payableReceivableRepository,
      TransactionRepository transactionRepository) {
    return new GetTransaction(
        accountClient, getCategory, payableReceivableRepository, transactionRepository);
  }

  @Bean
  public UpdateTransaction updateTransaction(
      EventPublisher eventPublisher,
      CategoryRepository categoryRepository,
      PayableReceivableRepository payableReceivableRepository,
      TransactionRepository transactionRepository,
      AccountSummaryCalculator accountSummaryCalculator) {
    return new UpdateTransaction(
        eventPublisher,
        categoryRepository,
        payableReceivableRepository,
        transactionRepository,
        accountSummaryCalculator);
  }

  @Bean
  public AccountEventListener accountEventListener(
      GetCategory getCategory,
      CreateTransaction createTransaction,
      CreateAccountSummary createAccountSummary) {
    return new AccountEventListener(getCategory, createTransaction, createAccountSummary);
  }

  @Bean
  public CreateTransfer createTransfer(
      EventPublisher eventPublisher,
      CreateTransaction createTransaction,
      TransferRepository transferRepository,
      TransactionRepository transactionRepository) {
    return new CreateTransfer(
        eventPublisher, createTransaction, transferRepository, transactionRepository);
  }

  @Bean
  public TransactionService transactionService(
      CreateTransaction createTransaction,
      CreatePayableReceivable createPayableReceivable,
      UpdateTransaction updateTransaction,
      GetTransaction getTransaction,
      CreateTransfer createTransfer) {
    return new TransactionServiceImpl(
        createTransaction,
        createPayableReceivable,
        updateTransaction,
        getTransaction,
        createTransfer);
  }
}
