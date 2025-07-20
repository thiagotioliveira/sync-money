package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import java.util.UUID;

public class GetTransaction {

  private final TransactionPort transactionPort;

  public GetTransaction(TransactionPort transactionPort) {
    this.transactionPort = transactionPort;
  }

  public Page<TransactionEnriched> byAccountId(UUID accountId, Pageable pageable) {
    return this.transactionPort.findByAccountId(accountId, pageable);
  }
}
