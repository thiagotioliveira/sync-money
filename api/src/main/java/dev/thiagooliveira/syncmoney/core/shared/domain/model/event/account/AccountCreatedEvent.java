package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountCreatedEvent implements DomainEvent {
  private final UUID id;
  private final String name;
  private final UUID bankId;
  private final UUID organizationId;
  private final UUID userId;
  private final BigDecimal initialBalance;
  private final OffsetDateTime dateTime;

  public AccountCreatedEvent(
      UUID id,
      String name,
      UUID organizationId,
      UUID userId,
      UUID bankId,
      BigDecimal initialBalance,
      OffsetDateTime dateTime) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
    this.userId = userId;
    this.bankId = bankId;
    this.initialBalance = initialBalance;
    this.dateTime = dateTime;
  }

  public UUID getBankId() {
    return bankId;
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

  @Override
  public String toString() {
    return "AccountCreatedEvent{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", bankId="
        + bankId
        + ", organizationId="
        + organizationId
        + ", userId="
        + userId
        + ", initialBalance="
        + initialBalance
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
