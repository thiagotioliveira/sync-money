package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountCreatedEvent implements Event {
  private final UUID id;
  private final String name;
  private final UUID bankId;
  private final UUID organizationId;
  private final UUID userId;
  private final BigDecimal initialBalance;
  private final OffsetDateTime createdAt;

  public AccountCreatedEvent(
      UUID id,
      String name,
      UUID organizationId,
      UUID userId,
      UUID bankId,
      BigDecimal initialBalance,
      OffsetDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
    this.userId = userId;
    this.bankId = bankId;
    this.initialBalance = initialBalance;
    this.createdAt = createdAt;
  }

  public UUID getBankId() {
    return bankId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public String getName() {
    return name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public UUID getUserId() {
    return userId;
  }

  @Override
  public String toString() {
    return "AccountCreatedEvent{"
        + "bankId="
        + bankId
        + ", id="
        + id
        + ", name='"
        + name
        + '\''
        + ", organizationId="
        + organizationId
        + ", userId="
        + userId
        + ", initialBalance="
        + initialBalance
        + ", createdAt="
        + createdAt
        + '}';
  }
}
