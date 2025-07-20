package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetTransaction;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

  private final CreateTransaction createTransaction;
  private final CreateScheduledTransaction createScheduledTransaction;
  private final GetTransaction getTransaction;

  public TransactionService(
      CreateTransaction createTransaction,
      CreateScheduledTransaction createScheduledTransaction,
      GetTransaction getTransaction) {
    this.createTransaction = createTransaction;
    this.createScheduledTransaction = createScheduledTransaction;
    this.getTransaction = getTransaction;
  }

  @Transactional
  public Transaction create(CreateTransactionInput input) {
    return this.createTransaction.create(input);
  }

  @Transactional
  public ScheduledTransaction createScheduledTransaction(CreateScheduledTransactionInput input) {
    return this.createScheduledTransaction.execute(input);
  }

  public Page<TransactionEnriched> getByAccountId(UUID accountId, Pageable pageable) {
    return this.getTransaction.byAccountId(accountId, pageable);
  }
}
