package dev.thiagooliveira.syncmoney.core.transaction.application.service;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

  Transfer transfer(CreateTransferInput input);

  Transaction createDeposit(CreateTransactionInput input);

  Transaction createWithdraw(CreateTransactionInput input);

  PayableReceivable createPayable(CreatePayableReceivableInput input);

  PayableReceivable createReceivable(CreatePayableReceivableInput input);

  Transaction update(
      UUID organizationId, UUID accountId, UUID transactionId, UpdateTransactionInput input);

  Transaction pay(PayTransactionInput input);

  List<TransactionEnriched> getByAccountIds(
      UUID organizationId, List<UUID> accountIds, YearMonth yearMonth);
}
