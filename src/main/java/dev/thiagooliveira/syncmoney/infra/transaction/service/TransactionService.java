package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.ScheduledTransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetScheduledTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetTransaction;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

  private final CreateTransaction createTransaction;
  private final CreateScheduledTransaction createScheduledTransaction;
  private final GetTransaction getTransaction;
  private final GetScheduledTransaction getScheduledTransaction;

  public TransactionService(
      CreateTransaction createTransaction,
      CreateScheduledTransaction createScheduledTransaction,
      GetTransaction getTransaction,
      GetScheduledTransaction getScheduledTransaction) {
    this.createTransaction = createTransaction;
    this.createScheduledTransaction = createScheduledTransaction;
    this.getTransaction = getTransaction;
    this.getScheduledTransaction = getScheduledTransaction;
  }

  @Transactional
  public Transaction create(CreateTransactionInput input) {
    return this.createTransaction.create(input);
  }

  @Transactional
  public ScheduledTransaction createScheduledTransaction(CreateScheduledTransactionInput input) {
    return this.createScheduledTransaction.execute(input);
  }

  public Page<TransactionEnriched> getTransactionsByAccountId(UUID accountId, Pageable pageable) {
    return this.getTransaction.byAccountId(accountId, pageable);
  }

  @Transactional
  public List<ScheduledTransactionEnriched> getScheduledTransactionsByAccountId(
      UUID organizationId, UUID accountId, YearMonth yearMonth) {
    return this.getScheduledTransaction.byAccountId(organizationId, accountId, yearMonth);
  }
}
