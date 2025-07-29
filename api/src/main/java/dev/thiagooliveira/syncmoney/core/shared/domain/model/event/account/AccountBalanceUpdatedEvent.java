package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountBalanceUpdatedEvent implements Event {
  private final UUID accountId;
  private final UUID organizationId;
  private final BigDecimal balance;

  public AccountBalanceUpdatedEvent(UUID accountId, UUID organizationId, BigDecimal balance) {
    this.accountId = accountId;
    this.organizationId = organizationId;
    this.balance = balance;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }
}
