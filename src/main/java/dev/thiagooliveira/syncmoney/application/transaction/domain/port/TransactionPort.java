package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Installment;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionPort {

  Transaction create(CreateTransactionInput input);

  List<Installment> saveInstallments(List<Installment> installments);

  Installment saveInstallment(Installment installment);

  List<Transaction> findByAccountAndYearMonth(UUID accountId, YearMonth yearMonth);

  Page<Transaction> findByAccountId(UUID accountId, Pageable pageable);

  boolean existsByParentIdAndDueDate(UUID parendId, LocalDate dueDate);

  Optional<Transaction> findByOrganizationIdAndId(UUID organizationId, UUID id);
}
