package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionUpdatedEvent implements Event {

  private final UUID id;
  private final UUID organizationId;
  private final UUID accountId;
  private final OffsetDateTime dateTime;
  private final BigDecimal amount;

  public TransactionUpdatedEvent(
      UUID id, UUID organizationId, UUID accountId, OffsetDateTime dateTime, BigDecimal amount) {
    this.accountId = accountId;
    this.id = id;
    this.organizationId = organizationId;
    this.dateTime = dateTime;
    this.amount = amount;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public UUID getId() {
    return id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  @Override
  public String toString() {
    return "TransactionUpdatedEvent{"
        + "accountId="
        + accountId
        + ", id="
        + id
        + ", organizationId="
        + organizationId
        + ", dateTime="
        + dateTime
        + ", amount="
        + amount
        + '}';
  }
}
