package dev.thiagooliveira.syncmoney.core.transaction.domain.port.income;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.*;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryCalculator;
import java.time.YearMonth;

public class CalculateAccountSummaryEventListener {

  private final AccountSummaryCalculator accountSummaryCalculator;

  public CalculateAccountSummaryEventListener(AccountSummaryCalculator accountSummaryCalculator) {
    this.accountSummaryCalculator = accountSummaryCalculator;
  }

  public void on(TransactionScheduledCreatedEvent event) {
    this.accountSummaryCalculator.calculate(
        event.getOrganizationId(),
        event.getAccountId(),
        YearMonth.of(event.getDateTime().getYear(), event.getDateTime().getMonth()));
  }

  public void on(TransactionPaidEvent event) {
    this.accountSummaryCalculator.calculate(
        event.getOrganizationId(),
        event.getAccountId(),
        YearMonth.of(event.getDateTime().getYear(), event.getDateTime().getMonth()));
  }

  public void on(TransactionUpdatedEvent event) {
    this.accountSummaryCalculator.calculate(
        event.getOrganizationId(),
        event.getAccountId(),
        YearMonth.of(event.getDateTime().getYear(), event.getDateTime().getMonth()));
  }

  public void on(PayableReceivableCreatedEvent event) {
    this.accountSummaryCalculator.calculate(
        event.getOrganizationId(),
        event.getAccountId(),
        YearMonth.of(event.getStartDate().getYear(), event.getStartDate().getMonth()));
  }

  public void on(PayableReceivableUpdatedEvent event) {
    this.accountSummaryCalculator.calculate(
        event.getOrganizationId(),
        event.getAccountId(),
        YearMonth.of(event.getStartDate().getYear(), event.getStartDate().getMonth()));
  }
}
