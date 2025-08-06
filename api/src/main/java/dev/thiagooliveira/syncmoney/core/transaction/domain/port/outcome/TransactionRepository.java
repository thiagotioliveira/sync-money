package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Category;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

  Transaction create(CreateTransactionInput input);

  List<Installment> createInstallments(List<Installment> installments);

  Installment createInstallment(Installment installment);

  Transaction update(Transaction transaction);

  Transaction pay(Transaction transaction, Category category);

  List<Transaction> getByAccountAndYearMonth(UUID accountId, YearMonth yearMonth);

  List<Transaction> getByAccountsAndYearMonth(List<UUID> accountIds, YearMonth yearMonth);

  List<Transaction> findByParentId(UUID parentId);

  boolean existsByParentIdAndDueDate(UUID parendId, LocalDate dueDate);

  Optional<Transaction> getById(UUID organizationId, UUID accountId, UUID id);
}
