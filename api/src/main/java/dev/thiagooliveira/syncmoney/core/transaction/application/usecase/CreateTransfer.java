package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransferInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransferRepository;

public class CreateTransfer {

  private final CreateTransaction createTransaction;
  private final TransferRepository transferRepository;
  private final TransactionRepository transactionRepository;

  public CreateTransfer(
      CreateTransaction createTransaction,
      TransferRepository transferRepository,
      TransactionRepository transactionRepository) {
    this.createTransaction = createTransaction;
    this.transferRepository = transferRepository;
    this.transactionRepository = transactionRepository;
  }

  public Transfer execute(CreateTransferInput input) {
    var transfer = this.transferRepository.create(input).addTransferCreatedEvent();
    var debitTransaction =
        this.createTransaction.withdraw(
            input.toCreateDebitTransaction(
                transfer.getDateTime(), "transfer #" + transfer.getId()));
    var creditTransaction =
        this.createTransaction.deposit(
            input.toCreateCreditTransaction(
                transfer.getDateTime(), "transfer #" + transfer.getId()));
    debitTransaction.setTransfer(transfer.getId());
    this.transactionRepository.update(debitTransaction);
    transfer.registerEvents(debitTransaction.getEvents());
    creditTransaction.setTransfer(transfer.getId());
    this.transactionRepository.update(creditTransaction);
    transfer.registerEvents(creditTransaction.getEvents());
    return transfer;
  }
}
