package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.util.UUID;

public class BankCreatedEvent implements Event {
  private UUID id;
  private UUID organizationId;
  private String name;
  private Currency currency;

  public BankCreatedEvent(Bank bank) {
    this.id = bank.getId();
    this.organizationId = bank.getOrganizationId();
    this.name = bank.getName();
    this.currency = bank.getCurrency();
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
