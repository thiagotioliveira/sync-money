package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.GetTransaction;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

  private final CreateTransaction createTransaction;
  private final GetTransaction getTransaction;

  public TransactionService(CreateTransaction createTransaction, GetTransaction getTransaction) {
    this.createTransaction = createTransaction;
    this.getTransaction = getTransaction;
  }

  @Transactional
  public Transaction create(CreateTransactionInput input) {
    return this.createTransaction.execute(input);
  }

  @Transactional
  public Transaction createPayableReceivable(CreatePayableReceivableInput input) {
    return this.createTransaction.execute(input);
  }

  @Transactional
  public List<Transaction> getByAccountId(
      UUID organizationId, UUID accountId, YearMonth yearMonth) {
    return this.getTransaction.byAccountId(organizationId, accountId, yearMonth);
  }

  @Transactional
  public Page<Transaction> getByAccountId(UUID organizationId, UUID accountId, Pageable pageable) {
    return this.getTransaction.byAccountId(organizationId, accountId, pageable);
  }
}
