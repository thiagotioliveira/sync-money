package dev.thiagooliveira.syncmoney.core.transaction.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.TransactionEnriched;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.AccountClient;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.PayableReceivableRepository;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome.TransactionRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetTransaction {

  private final AccountClient accountClient;
  private final GetCategory getCategory;
  private final PayableReceivableRepository payableReceivableRepository;
  private final TransactionRepository transactionRepository;

  public GetTransaction(
      AccountClient accountClient,
      GetCategory getCategory,
      PayableReceivableRepository payableReceivableRepository,
      TransactionRepository transactionRepository) {
    this.accountClient = accountClient;
    this.getCategory = getCategory;
    this.payableReceivableRepository = payableReceivableRepository;
    this.transactionRepository = transactionRepository;
  }

  public Optional<TransactionEnriched> byId(UUID organizationId, UUID accountId, UUID id) {
    validAccount(organizationId, accountId);
    return this.transactionRepository
        .getById(organizationId, accountId, id)
        .map(
            t -> {
              var category =
                  this.getCategory.getById(organizationId, t.getCategoryId()).orElseThrow();
              return new TransactionEnriched(t, category);
            });
  }

  public List<TransactionEnriched> byAccountIdAndYearMonth(
      UUID organizationId, UUID accountId, YearMonth yearMonth) {
    validAccount(organizationId, accountId);
    saveInstallmentsIfNeeded(accountId, yearMonth);
    return this.transactionRepository.getByAccountAndYearMonth(accountId, yearMonth).stream()
        .map(
            t -> {
              var category =
                  this.getCategory.getById(organizationId, t.getCategoryId()).orElseThrow();
              return new TransactionEnriched(t, category);
            })
        .toList();
  }

  private void saveInstallmentsIfNeeded(UUID accountId, YearMonth yearMonth) {
    var recurringList =
        this.payableReceivableRepository.getRecurringByAccountId(accountId, yearMonth);
    for (var payableReceivable : recurringList) {
      var dayBase = payableReceivable.getStartDate().getDayOfMonth();
      var dueDate = yearMonth.atDay(dayBase);
      if (!this.transactionRepository.existsByParentIdAndDueDate(
          payableReceivable.getId(), dueDate)) {
        this.transactionRepository.createInstallment(
            payableReceivable.generateInstallment(dueDate));
      }
    }
  }

  private void validAccount(UUID organizationId, UUID accountId) {
    if (!this.accountClient.existsById(organizationId, accountId)) {
      throw BusinessLogicException.notFound("account not found");
    }
  }
}
