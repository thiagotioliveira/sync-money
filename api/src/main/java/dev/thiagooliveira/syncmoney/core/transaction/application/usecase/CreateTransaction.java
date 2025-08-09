package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;

public class CreateTransaction {

  private final AccountClient accountClient;
  private final GetCategory getCategory;
  private final TransactionRepository transactionRepository;

  public CreateTransaction(
      AccountClient accountClient,
      GetCategory getCategory,
      TransactionRepository transactionRepository) {
    this.accountClient = accountClient;
    this.getCategory = getCategory;
    this.transactionRepository = transactionRepository;
  }

  public Transaction deposit(CreateTransactionInput input) {
    if (!this.accountClient.existsById(input.organizationId(), input.accountId())) {
      throw BusinessLogicException.notFound("account not found");
    }
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.getType().isCredit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    return this.transactionRepository.create(input).paid(category.getType());
  }

  public Transaction withdraw(CreateTransactionInput input) {
    if (!this.accountClient.existsById(input.organizationId(), input.accountId())) {
      throw BusinessLogicException.notFound("account not found");
    }
    var category =
        this.getCategory
            .getById(input.organizationId(), input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    if (!category.getType().isDebit()) {
      throw BusinessLogicException.badRequest("category type not supported");
    }
    return this.transactionRepository.create(input).paid(category.getType());
  }
}
