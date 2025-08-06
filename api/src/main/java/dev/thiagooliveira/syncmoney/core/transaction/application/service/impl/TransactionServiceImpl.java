package dev.thiagooliveira.syncmoney.core.transaction.application.service.impl;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

  private final CreateTransaction createTransaction;
  private final CreatePayableReceivable createPayableReceivable;
  private final UpdateTransaction updateTransaction;
  private final GetTransaction getTransaction;
  private final CreateTransfer createTransfer;

  public TransactionServiceImpl(
      CreateTransaction createTransaction,
      CreatePayableReceivable createPayableReceivable,
      UpdateTransaction updateTransaction,
      GetTransaction getTransaction,
      CreateTransfer createTransfer) {
    this.createTransaction = createTransaction;
    this.createPayableReceivable = createPayableReceivable;
    this.updateTransaction = updateTransaction;
    this.getTransaction = getTransaction;
    this.createTransfer = createTransfer;
  }

  @Override
  public Transfer transfer(CreateTransferInput input) {
    return this.createTransfer.execute(input);
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
  public List<TransactionEnriched> getByAccountIds(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.getTransaction.byAccountIdsAndYearMonth(organizationId, accountIds, yearMonth);
  }
}
