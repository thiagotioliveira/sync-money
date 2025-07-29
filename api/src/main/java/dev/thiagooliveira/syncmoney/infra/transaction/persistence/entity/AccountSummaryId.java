package dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class AccountSummaryId {

  private UUID accountId;
  private LocalDate yearMonth;

  public AccountSummaryId() {}

  public AccountSummaryId(UUID accountId, LocalDate yearMonth) {
    this.accountId = accountId;
    this.yearMonth = yearMonth;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public LocalDate getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(LocalDate yearMonth) {
    this.yearMonth = yearMonth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountSummaryId that)) return false;
    return Objects.equals(accountId, that.accountId) && Objects.equals(yearMonth, that.yearMonth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, yearMonth);
  }
}
