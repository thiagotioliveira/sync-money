package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.PayableReceivablePort;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetTransaction {

  private final GetAccount getAccount;
  private final PayableReceivablePort payableReceivablePort;
  private final TransactionPort transactionPort;

  public GetTransaction(
      GetAccount getAccount,
      PayableReceivablePort payableReceivablePort,
      TransactionPort transactionPort) {
    this.getAccount = getAccount;
    this.payableReceivablePort = payableReceivablePort;
    this.transactionPort = transactionPort;
  }

  public Optional<Transaction> byId(UUID organizationId, UUID accountId, UUID id) {
    validAccount(organizationId, accountId);
    return this.transactionPort.findByOrganizationIdAndId(organizationId, id);
  }

  public Page<Transaction> byAccountId(UUID organizationId, UUID accountId, Pageable pageable) {
    return this.transactionPort.findByAccountId(accountId, pageable);
  }

  public List<Transaction> byAccountId(UUID organizationId, UUID accountId, YearMonth yearMonth) {
    validAccount(organizationId, accountId);
    saveInstallmentsIfNeeded(accountId, yearMonth);
    return this.transactionPort.findByAccountAndYearMonth(accountId, yearMonth);
  }

  private void saveInstallmentsIfNeeded(UUID accountId, YearMonth yearMonth) {
    var recurringList = this.payableReceivablePort.findRecurringByAccountId(accountId, yearMonth);
    for (var payableReceivable : recurringList) {
      var dayBase = payableReceivable.startDate().getDayOfMonth();
      var dueDate = yearMonth.atDay(dayBase);
      if (!this.transactionPort.existsByParentIdAndDueDate(payableReceivable.id(), dueDate)) {
        this.transactionPort.saveInstallment(payableReceivable.generateInstallment(dueDate));
      }
    }
  }

  private void validAccount(UUID organizationId, UUID accountId) {
    var account =
        this.getAccount
            .findById(organizationId, accountId)
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
  }
}
