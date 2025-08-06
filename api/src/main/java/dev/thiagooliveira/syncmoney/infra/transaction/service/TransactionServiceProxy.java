package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class TransactionServiceProxy implements TransactionService {

  private final TransactionService transactionService;

  public TransactionServiceProxy(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Transactional
  @Override
  public Transaction createDeposit(CreateTransactionInput input) {
    return this.transactionService.createDeposit(input);
  }

  @Transactional
  @Override
  public Transaction createWithdraw(CreateTransactionInput input) {
    return this.transactionService.createWithdraw(input);
  }

  @Transactional
  @Override
  public PayableReceivable createPayable(CreatePayableReceivableInput input) {
    return this.transactionService.createPayable(input);
  }

  @Transactional
  @Override
  public PayableReceivable createReceivable(CreatePayableReceivableInput input) {
    return this.transactionService.createReceivable(input);
  }

  @Transactional
  @Override
  public Transaction update(
      UUID organizationId, UUID accountId, UUID transactionId, UpdateTransactionInput input) {
    return this.transactionService.update(organizationId, accountId, transactionId, input);
  }

  @Transactional
  @Override
  public Transaction pay(PayTransactionInput input) {
    return this.transactionService.pay(input);
  }

  @Transactional(readOnly = true)
  @Override
  public List<TransactionEnriched> getByAccountIds(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth) {
    return this.transactionService.getByAccountIds(organizationId, accountIds, yearMonth);
  }
}
