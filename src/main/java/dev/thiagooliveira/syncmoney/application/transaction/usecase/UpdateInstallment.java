package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.UpdateInstallmentInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import java.util.UUID;

public class UpdateInstallment {

  private final GetTransaction getTransaction;
  private final TransactionPort transactionPort;

  public UpdateInstallment(GetTransaction getTransaction, TransactionPort transactionPort) {
    this.getTransaction = getTransaction;
    this.transactionPort = transactionPort;
  }

  public Installment execute(
      UUID organizationId, UUID accountId, UUID installmentId, UpdateInstallmentInput input) {
    var installment =
        this.getTransaction
            .byId(organizationId, accountId, installmentId)
            .orElseThrow(() -> BusinessLogicException.notFound("installment not found."))
            .toInstallment();
    return this.transactionPort.saveInstallment(installment.update(input));
  }
}
