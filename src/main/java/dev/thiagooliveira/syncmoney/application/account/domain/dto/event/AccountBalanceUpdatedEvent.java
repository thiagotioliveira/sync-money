package dev.thiagooliveira.syncmoney.application.account.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountBalanceUpdatedEvent implements Event {
  private final UUID accountId;
  private final UUID organizationId;
  private final BigDecimal balance;

  public AccountBalanceUpdatedEvent(Account account) {
    this.accountId = account.id();
    this.organizationId = account.organizationId();
    this.balance = account.balance();
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
