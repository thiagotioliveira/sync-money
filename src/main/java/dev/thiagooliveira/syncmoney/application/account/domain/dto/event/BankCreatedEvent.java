package dev.thiagooliveira.syncmoney.application.account.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.application.event.domain.dto.Event;
import java.util.UUID;

public class BankCreatedEvent implements Event {
  private UUID id;
  private UUID organizationId;
  private String name;
  private Currency currency;

  public BankCreatedEvent(Bank bank) {
    this.id = bank.id();
    this.organizationId = bank.organizationId();
    this.name = bank.name();
    this.currency = bank.currency();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
