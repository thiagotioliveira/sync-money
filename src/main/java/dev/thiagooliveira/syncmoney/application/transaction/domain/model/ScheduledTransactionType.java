package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

public enum ScheduledTransactionType {
  PAYABLE,
  RECEIVABLE;

  public boolean isPayable() {
    return this.equals(PAYABLE);
  }

  public boolean isReceivable() {
    return this.equals(RECEIVABLE);
  }
}
