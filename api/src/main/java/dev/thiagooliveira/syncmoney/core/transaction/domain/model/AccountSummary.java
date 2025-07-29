package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.TransactionEnriched;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class AccountSummary {

  private final UUID accountId;
  private final YearMonth yearMonth;
  private BigDecimal openingBalance;
  private BigDecimal closingBalance;
  private BigDecimal totalDebit;
  private BigDecimal totalCredit;
  private BigDecimal scheduledIncome;
  private BigDecimal scheduledExpenses;

  private AccountSummary(UUID accountId, YearMonth yearMonth) {
    this.accountId = accountId;
    this.yearMonth = yearMonth;
    this.openingBalance = BigDecimal.ZERO;
    this.closingBalance = BigDecimal.ZERO;
    this.totalDebit = BigDecimal.ZERO;
    this.totalCredit = BigDecimal.ZERO;
    this.scheduledIncome = BigDecimal.ZERO;
    this.scheduledExpenses = BigDecimal.ZERO;
  }

  public AccountSummary(
      UUID accountId,
      YearMonth yearMonth,
      BigDecimal openingBalance,
      BigDecimal closingBalance,
      BigDecimal totalDebit,
      BigDecimal totalCredit,
      BigDecimal scheduledIncome,
      BigDecimal scheduledExpenses) {
    this.accountId = accountId;
    this.yearMonth = yearMonth;
    this.openingBalance = openingBalance;
    this.closingBalance = closingBalance;
    this.totalDebit = totalDebit;
    this.totalCredit = totalCredit;
    this.scheduledIncome = scheduledIncome;
    this.scheduledExpenses = scheduledExpenses;
  }

  public static AccountSummary create(UUID accountId, YearMonth yearMonth) {
    return new AccountSummary(accountId, yearMonth);
  }

  public static AccountSummary create(CreateAccountSummaryInput input) {
    var summary = new AccountSummary(input.accountId(), input.yearMonth());
    boolean isCredit = input.lastClosingBalance().signum() > 0;
    summary.openingBalance =
        isCredit ? input.lastClosingBalance() : input.lastClosingBalance().negate();
    summary.update(input.transactionsMonth());
    return summary;
  }

  public void update(List<TransactionEnriched> transactions) {
    if (transactions.stream()
        .anyMatch(
            t ->
                !this.yearMonth.equals(
                    YearMonth.of(t.dueDate().getYear(), t.dueDate().getMonth())))) {
      throw BusinessLogicException.badRequest("transactions must be from the same month");
    }
    this.totalDebit =
        transactions.stream()
            .filter(TransactionEnriched::isPaid)
            .filter(TransactionEnriched::isDebit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.totalCredit =
        transactions.stream()
            .filter(TransactionEnriched::isPaid)
            .filter(TransactionEnriched::isCredit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.scheduledIncome =
        transactions.stream()
            .filter(TransactionEnriched::isScheduled)
            .filter(TransactionEnriched::isCredit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.scheduledExpenses =
        transactions.stream()
            .filter(TransactionEnriched::isScheduled)
            .filter(TransactionEnriched::isDebit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.closingBalance =
        this.openingBalance
            .add(totalCredit.subtract(totalDebit))
            .add(scheduledIncome.subtract(scheduledExpenses));
  }

  public UUID getAccountId() {
    return accountId;
  }

  public YearMonth getYearMonth() {
    return yearMonth;
  }

  public BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public BigDecimal getClosingBalance() {
    return closingBalance;
  }

  public BigDecimal getTotalDebit() {
    return totalDebit;
  }

  public BigDecimal getTotalCredit() {
    return totalCredit;
  }

  public BigDecimal getScheduledIncome() {
    return scheduledIncome;
  }

  public BigDecimal getScheduledExpenses() {
    return scheduledExpenses;
  }
}
