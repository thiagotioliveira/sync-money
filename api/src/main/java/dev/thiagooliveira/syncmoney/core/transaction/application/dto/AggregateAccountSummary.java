package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import java.util.*;

public class AggregateAccountSummary {

  private final List<AccountSummary> summaries;
  private final List<CurrencySummary> totalByCurrency;

  public AggregateAccountSummary(
      List<AccountSummary> summaries, Map<UUID, Currency> accountCurrencyMap) {
    this.summaries = summaries;
    this.totalByCurrency = new ArrayList<>();
    var map = new HashMap<Currency, CurrencySummary>();
    this.summaries.forEach(
        summary -> {
          var currency = accountCurrencyMap.get(summary.getAccountId());
          if (currency == null) {
            throw new IllegalArgumentException(
                "currency not found for account id: " + summary.getAccountId());
          }
          var currencySummary = map.computeIfAbsent(currency, CurrencySummary::new);
          currencySummary.summary.setOpeningBalance(
              currencySummary.summary.getOpeningBalance().add(summary.getOpeningBalance()));
          currencySummary.summary.setClosingBalance(
              currencySummary.summary.getClosingBalance().add(summary.getClosingBalance()));
          currencySummary.summary.setTotalCredit(
              currencySummary.summary.getTotalCredit().add(summary.getTotalCredit()));
          currencySummary.summary.setTotalDebit(
              currencySummary.summary.getTotalDebit().add(summary.getTotalDebit()));
          currencySummary.summary.setScheduledIncome(
              currencySummary.summary.getScheduledIncome().add(summary.getScheduledIncome()));
          currencySummary.summary.setScheduledExpenses(
              currencySummary.summary.getScheduledExpenses().add(summary.getScheduledExpenses()));
          this.totalByCurrency.add(currencySummary);
        });
  }

  public List<AccountSummary> getSummaries() {
    return summaries;
  }

  public List<CurrencySummary> getTotalByCurrency() {
    return totalByCurrency;
  }

  public static class CurrencySummary {
    private final Currency currency;
    private final AccountSummary.Summary summary;

    public CurrencySummary(Currency currency) {
      this.currency = currency;
      this.summary = new AccountSummary.Summary();
    }

    public Currency getCurrency() {
      return currency;
    }

    public AccountSummary.Summary getSummary() {
      return summary;
    }
  }
}
