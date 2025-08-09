package dev.thiagooliveira.syncmoney.core.account.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.BankCreatedEvent;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Bank {
  private UUID id;
  private UUID organizationId;
  private String name;
  private Currency currency;

  private Bank() {}

  public static Bank restore(UUID id, UUID organizationId, String name, Currency currency) {
    var bank = new Bank();
    bank.id = id;
    bank.organizationId = organizationId;
    bank.name = name;
    bank.currency = currency;
    return bank;
  }

  public Bank created() {
    DomainEventPublisher.addEvent(
        new BankCreatedEvent(this.id, this.name, this.organizationId, OffsetDateTime.now()));
    return this;
  }

  public Currency getCurrency() {
    return currency;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }
}
