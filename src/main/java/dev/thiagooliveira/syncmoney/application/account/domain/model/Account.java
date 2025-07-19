package dev.thiagooliveira.syncmoney.application.account.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Account(
    UUID id,
    String name,
    Bank bank,
    UUID organizationId,
    BigDecimal balance,
    OffsetDateTime createdAt,
    Long version) {

  public Account withdraw(BigDecimal amount) {
    return new Account(
        id, name, bank, organizationId, this.balance.subtract(amount), createdAt, version);
  }

  public Account deposit(BigDecimal amount) {
    return new Account(
        id, name, bank, organizationId, this.balance.add(amount), createdAt, version);
  }
}
