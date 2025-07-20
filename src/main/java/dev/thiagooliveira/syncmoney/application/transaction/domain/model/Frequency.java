package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

public enum Frequency {
  MONTHLY;

  public boolean isMonthly() {
    return this.equals(MONTHLY);
  }
}
