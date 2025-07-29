package dev.thiagooliveira.syncmoney.core.transaction.application.service.impl;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.CreateTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.GetTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.UpdateTransaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

  private final CreateTransaction createTransaction;
  private final CreatePayableReceivable createPayableReceivable;
  private final UpdateTransaction updateTransaction;
  private final GetTransaction getTransaction;

  public TransactionServiceImpl(
      CreateTransaction createTransaction,
      CreatePayableReceivable createPayableReceivable,
      UpdateTransaction updateTransaction,
      GetTransaction getTransaction) {
    this.createTransaction = createTransaction;
    this.createPayableReceivable = createPayableReceivable;
    this.updateTransaction = updateTransaction;
    this.getTransaction = getTransaction;
  }

  @Override
  public Transaction createDeposit(CreateTransactionInput input) {
    return this.createTransaction.deposit(input);
  }

  @Override
  public Transaction createWithdraw(CreateTransactionInput input) {
    return this.createTransaction.withdraw(input);
  }

  @Override
  public PayableReceivable createPayable(CreatePayableReceivableInput input) {
    return this.createPayableReceivable.payable(input);
  }

  @Override
  public PayableReceivable createReceivable(CreatePayableReceivableInput input) {
    return this.createPayableReceivable.receivable(input);
  }

  @Override
  public Transaction update(
      UUID organizationId, UUID accountId, UUID transactionId, UpdateTransactionInput input) {
    return this.updateTransaction.update(organizationId, accountId, transactionId, input);
  }

  @Override
  public Transaction pay(PayTransactionInput input) {
    return this.updateTransaction.pay(input);
  }

  @Override
  public List<TransactionEnriched> getByAccountId(
      UUID organizationId, UUID accountId, YearMonth yearMonth) {
    return this.getTransaction.byAccountIdAndYearMonth(organizationId, accountId, yearMonth);
  }
}
