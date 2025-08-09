package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountBalanceUpdatedEvent implements DomainEvent {
  private final UUID accountId;
  private final UUID organizationId;
  private final BigDecimal balance;
  private final UUID userId;
  private final OffsetDateTime dateTime;

  public AccountBalanceUpdatedEvent(
      UUID accountId,
      UUID organizationId,
      BigDecimal balance,
      UUID userId,
      OffsetDateTime dateTime) {
    this.accountId = accountId;
    this.organizationId = organizationId;
    this.balance = balance;
    this.userId = userId;
    this.dateTime = dateTime;
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

  @Override
  public String toString() {
    return "AccountBalanceUpdatedEvent{"
        + "accountId="
        + accountId
        + ", organizationId="
        + organizationId
        + ", balance="
        + balance
        + ", userId="
        + userId
        + ", dateTime="
        + dateTime
        + '}';
  }

  public UUID getUserId() {
    return userId;
  }

  @Override
  public OffsetDateTime getDateTime() {
    return dateTime;
  }
}
