package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "account_summaries")
public class AccountSummaryEntity {

  @EmbeddedId private AccountSummaryId id;

  @Column(name = "opening_balance", nullable = false)
  private BigDecimal openingBalance;

  @Column(name = "closing_balance", nullable = false)
  private BigDecimal closingBalance;

  @Column(name = "total_debit", nullable = false)
  private BigDecimal totalDebit;

  @Column(name = "total_credit", nullable = false)
  private BigDecimal totalCredit;

  @Column(name = "scheduled_income", nullable = false)
  private BigDecimal scheduledIncome;

  @Column(name = "scheduled_expenses", nullable = false)
  private BigDecimal scheduledExpenses;

  public AccountSummary toAccountSummary() {
    return new AccountSummary(
        this.id.getAccountId(),
        YearMonth.of(this.id.getYearMonth().getYear(), this.id.getYearMonth().getMonth()),
        this.openingBalance,
        this.closingBalance,
        this.totalDebit,
        this.totalCredit,
        this.scheduledIncome,
        this.scheduledExpenses);
  }

  public static AccountSummaryEntity from(AccountSummary accountSummary) {
    AccountSummaryEntity entity = new AccountSummaryEntity();
    entity.id =
        new AccountSummaryId(accountSummary.getAccountId(), accountSummary.getYearMonth().atDay(1));
    entity.openingBalance = accountSummary.getOpeningBalance();
    entity.closingBalance = accountSummary.getClosingBalance();
    entity.totalDebit = accountSummary.getTotalDebit();
    entity.totalCredit = accountSummary.getTotalCredit();
    entity.scheduledIncome = accountSummary.getScheduledIncome();
    entity.scheduledExpenses = accountSummary.getScheduledExpenses();
    return entity;
  }

  public BigDecimal getClosingBalance() {
    return closingBalance;
  }

  public void setClosingBalance(BigDecimal closingBalance) {
    this.closingBalance = closingBalance;
  }

  public AccountSummaryId getId() {
    return id;
  }

  public void setId(AccountSummaryId id) {
    this.id = id;
  }

  public BigDecimal getOpeningBalance() {
    return openingBalance;
  }

  public void setOpeningBalance(BigDecimal openingBalance) {
    this.openingBalance = openingBalance;
  }

  public BigDecimal getScheduledExpenses() {
    return scheduledExpenses;
  }

  public void setScheduledExpenses(BigDecimal scheduledExpenses) {
    this.scheduledExpenses = scheduledExpenses;
  }

  public BigDecimal getScheduledIncome() {
    return scheduledIncome;
  }

  public void setScheduledIncome(BigDecimal scheduledIncome) {
    this.scheduledIncome = scheduledIncome;
  }

  public BigDecimal getTotalCredit() {
    return totalCredit;
  }

  public void setTotalCredit(BigDecimal totalCredit) {
    this.totalCredit = totalCredit;
  }

  public BigDecimal getTotalDebit() {
    return totalDebit;
  }

  public void setTotalDebit(BigDecimal totalDebit) {
    this.totalDebit = totalDebit;
  }
}
