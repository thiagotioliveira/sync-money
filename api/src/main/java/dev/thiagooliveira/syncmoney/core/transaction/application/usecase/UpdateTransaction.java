package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.PayTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.UpdateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.CategoryRepository;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.PayableReceivableRepository;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import java.util.Optional;
import java.util.UUID;

public class UpdateTransaction {

  private final CategoryRepository categoryRepository;
  private final PayableReceivableRepository payableReceivableRepository;
  private final TransactionRepository transactionRepository;

  public UpdateTransaction(
      CategoryRepository categoryRepository,
      PayableReceivableRepository payableReceivableRepository,
      TransactionRepository transactionRepository) {
    this.categoryRepository = categoryRepository;
    this.payableReceivableRepository = payableReceivableRepository;
    this.transactionRepository = transactionRepository;
  }

  public Transaction update(
      UUID organizationId, UUID accountId, UUID transactionId, UpdateTransactionInput input) {
    var transaction =
        this.transactionRepository
            .getById(organizationId, accountId, transactionId)
            .orElseThrow(() -> BusinessLogicException.notFound("transaction not found."));

    var transactionUpdated =
        this.transactionRepository
            .update(transaction.update(input.dueDate(), input.amount()))
            .updated();

    if (input.applyNextInstallments() && transactionUpdated.getParentId().isPresent()) {
      this.payableReceivableRepository
          .getById(organizationId, transactionUpdated.getParentId().get())
          .ifPresent(
              payableReceivable -> {
                payableReceivable =
                    this.payableReceivableRepository
                        .update(payableReceivable.update(Optional.empty(), input.amount()))
                        .updated();

                var transactions =
                    this.transactionRepository.findByParentId(payableReceivable.getId());
                transactions.stream()
                    .filter(Transaction::isScheduled)
                    .filter(t -> t.getDueDate().isAfter(transactionUpdated.getDueDate()))
                    .forEach(
                        i -> {
                          var updated =
                              this.transactionRepository
                                  .update(i.update(input.dueDate(), input.amount()))
                                  .updated();
                        });
              });
    }

    return transactionUpdated;
  }

  public Transaction pay(PayTransactionInput input) {
    var transaction =
        this.transactionRepository
            .getById(input.organizationId(), input.accountId(), input.transactionId())
            .orElseThrow(() -> BusinessLogicException.notFound("transaction not found."));
    var category =
        this.categoryRepository
            .getById(input.organizationId(), transaction.getCategoryId())
            .orElseThrow();
    return this.transactionRepository
        .pay(transaction.pay(input.userId(), input.dateTime(), input.amount()), category)
        .paid(category.getType());
  }
}
