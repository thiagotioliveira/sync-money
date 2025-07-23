package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.TransactionCreatedEvent;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;

public class CreateTransaction {

  private final EventPublisher eventPublisher;
  private final GetAccount getAccount;
  private final GetCategory getCategory;
  private final TransactionPort transactionPort;

  public CreateTransaction(
      EventPublisher eventPublisher,
      GetAccount getAccount,
      GetCategory getCategory,
      TransactionPort transactionPort) {
    this.eventPublisher = eventPublisher;
    this.getAccount = getAccount;
    this.getCategory = getCategory;
    this.transactionPort = transactionPort;
  }

  public Transaction create(CreateTransactionInput input) {
    var account =
        this.getAccount
            .findById(input.organizationId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    var category =
        this.getCategory
            .findById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    var transaction = this.transactionPort.create(input);
    this.eventPublisher.publish(
        new TransactionCreatedEvent(input.organizationId(), category.type(), transaction));
    return transaction;
  }
}
