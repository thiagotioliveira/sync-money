package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventScoped;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.application.usecase.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

  private final EventPublisher eventPublisher;
  private final CreateTransaction createTransaction;
  private final CreatePayableReceivable createPayableReceivable;
  private final UpdateTransaction updateTransaction;
  private final GetTransaction getTransaction;
  private final CreateTransfer createTransfer;

  public TransactionServiceImpl(
      EventPublisher eventPublisher,
      CreateTransaction createTransaction,
      CreatePayableReceivable createPayableReceivable,
      UpdateTransaction updateTransaction,
      GetTransaction getTransaction,
      CreateTransfer createTransfer) {
    this.eventPublisher = eventPublisher;
    this.createTransaction = createTransaction;
    this.createPayableReceivable = createPayableReceivable;
    this.updateTransaction = updateTransaction;
    this.getTransaction = getTransaction;
    this.createTransfer = createTransfer;
  }

  @DomainEventScoped
  @Override
  public Transfer transfer(CreateTransferInput input) {
    Transfer transfer = this.createTransfer.execute(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return transfer;
  }

  @DomainEventScoped
  @Override
  public Transaction createDeposit(CreateTransactionInput input) {
    Transaction transaction = this.createTransaction.deposit(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return transaction;
  }

  @DomainEventScoped
  @Override
  public Transaction createWithdraw(CreateTransactionInput input) {
    Transaction transaction = this.createTransaction.withdraw(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return transaction;
  }

  @DomainEventScoped
  @Override
  public PayableReceivable createPayable(CreatePayableReceivableInput input) {
    PayableReceivable payableReceivable = this.createPayableReceivable.payable(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return payableReceivable;
  }

  @DomainEventScoped
  @Override
  public PayableReceivable createReceivable(CreatePayableReceivableInput input) {
    PayableReceivable payableReceivable = this.createPayableReceivable.receivable(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return payableReceivable;
  }

  @DomainEventScoped
  @Override
  public Transaction update(
      UUID organizationId, UUID accountId, UUID transactionId, UpdateTransactionInput input) {
    Transaction transaction =
        this.updateTransaction.update(organizationId, accountId, transactionId, input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return transaction;
  }

  @DomainEventScoped
  @Override
  public Transaction pay(PayTransactionInput input) {
    Transaction transaction = this.updateTransaction.pay(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return transaction;
  }

  @Override
  public List<TransactionEnriched> getByAccountIds(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.getTransaction.byAccountIdsAndYearMonth(organizationId, accountIds, yearMonth);
  }
}
