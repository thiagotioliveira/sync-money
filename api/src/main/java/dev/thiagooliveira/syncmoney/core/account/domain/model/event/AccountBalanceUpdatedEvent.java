package dev.thiagooliveira.syncmoney.core.account.domain.model.event;

import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountBalanceUpdatedEvent implements Event {
  private final UUID accountId;
  private final UUID organizationId;
  private final BigDecimal balance;

  public AccountBalanceUpdatedEvent(Account account) {
    this.accountId = account.getId();
    this.organizationId = account.getOrganizationId();
    this.balance = account.getBalance();
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
