package dev.thiagooliveira.syncmoney.core.transaction.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateAccountSummaryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.TransactionEnriched;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public class AccountSummary implements AggregateRoot {

  private final UUID accountId;
  private final YearMonth yearMonth;
  private final Summary summary;

  private AccountSummary(UUID accountId, YearMonth yearMonth) {
    this.accountId = accountId;
    this.yearMonth = yearMonth;
    this.summary = new Summary();
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
    this.summary = new Summary();
    this.summary.openingBalance = openingBalance;
    this.summary.closingBalance = closingBalance;
    this.summary.totalDebit = totalDebit;
    this.summary.totalCredit = totalCredit;
    this.summary.scheduledIncome = scheduledIncome;
    this.summary.scheduledExpenses = scheduledExpenses;
  }

  public static AccountSummary create(UUID accountId, YearMonth yearMonth) {
    return new AccountSummary(accountId, yearMonth);
  }

  public static AccountSummary create(CreateAccountSummaryInput input) {
    var accountSummary = new AccountSummary(input.accountId(), input.yearMonth());
    boolean isCredit = input.lastClosingBalance().signum() > 0;
    accountSummary.summary.openingBalance =
        isCredit ? input.lastClosingBalance() : input.lastClosingBalance().negate();
    accountSummary.update(input.transactionsMonth());
    return accountSummary;
  }

  public void update(List<TransactionEnriched> transactions) {
    if (transactions.stream()
        .anyMatch(
            t ->
                !this.yearMonth.equals(
                    YearMonth.of(t.dueDate().getYear(), t.dueDate().getMonth())))) {
      throw BusinessLogicException.badRequest("transactions must be from the same month");
    }
    this.summary.totalDebit =
        transactions.stream()
            .filter(TransactionEnriched::isPaid)
            .filter(TransactionEnriched::isDebit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.summary.totalCredit =
        transactions.stream()
            .filter(TransactionEnriched::isPaid)
            .filter(TransactionEnriched::isCredit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.summary.scheduledIncome =
        transactions.stream()
            .filter(TransactionEnriched::isScheduled)
            .filter(TransactionEnriched::isCredit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.summary.scheduledExpenses =
        transactions.stream()
            .filter(TransactionEnriched::isScheduled)
            .filter(TransactionEnriched::isDebit)
            .map(TransactionEnriched::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    this.summary.closingBalance =
        this.summary
            .openingBalance
            .add(this.summary.totalCredit.subtract(this.summary.totalDebit))
            .add(this.summary.scheduledIncome.subtract(this.summary.scheduledExpenses));
  }

  public UUID getAccountId() {
    return accountId;
  }

  public YearMonth getYearMonth() {
    return yearMonth;
  }

  public BigDecimal getOpeningBalance() {
    return summary.openingBalance;
  }

  public BigDecimal getClosingBalance() {
    return summary.closingBalance;
  }

  public BigDecimal getTotalDebit() {
    return summary.totalDebit;
  }

  public BigDecimal getTotalCredit() {
    return summary.totalCredit;
  }

  public BigDecimal getScheduledIncome() {
    return summary.scheduledIncome;
  }

  public BigDecimal getScheduledExpenses() {
    return summary.scheduledExpenses;
  }

  public static class Summary {
    private BigDecimal openingBalance = BigDecimal.ZERO;
    ;
    private BigDecimal closingBalance = BigDecimal.ZERO;
    ;
    private BigDecimal totalDebit = BigDecimal.ZERO;
    ;
    private BigDecimal totalCredit = BigDecimal.ZERO;
    ;
    private BigDecimal scheduledIncome = BigDecimal.ZERO;
    ;
    private BigDecimal scheduledExpenses = BigDecimal.ZERO;
    ;

    public BigDecimal getOpeningBalance() {
      return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
      this.openingBalance = openingBalance;
    }

    public BigDecimal getClosingBalance() {
      return closingBalance;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
      this.closingBalance = closingBalance;
    }

    public BigDecimal getTotalDebit() {
      return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
      this.totalDebit = totalDebit;
    }

    public BigDecimal getTotalCredit() {
      return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
      this.totalCredit = totalCredit;
    }

    public BigDecimal getScheduledIncome() {
      return scheduledIncome;
    }

    public void setScheduledIncome(BigDecimal scheduledIncome) {
      this.scheduledIncome = scheduledIncome;
    }

    public BigDecimal getScheduledExpenses() {
      return scheduledExpenses;
    }

    public void setScheduledExpenses(BigDecimal scheduledExpenses) {
      this.scheduledExpenses = scheduledExpenses;
    }
  }
}
