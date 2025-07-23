package dev.thiagooliveira.syncmoney.application.transaction.domain.model;

public enum CategoryType {
  CREDIT,
  DEBIT;

  public boolean isCredit() {
    return this == CategoryType.CREDIT;
  }

  public boolean isDebit() {
    return this == CategoryType.DEBIT;
  }
}
