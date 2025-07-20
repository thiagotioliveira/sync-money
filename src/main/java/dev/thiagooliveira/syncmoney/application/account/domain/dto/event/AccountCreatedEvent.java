package dev.thiagooliveira.syncmoney.application.account.domain.dto.event;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountCreatedEvent implements Event {
  private UUID id;
  private String name;
  private UUID bankId;
  private UUID organizationId;
  private OffsetDateTime createdAt;

  public AccountCreatedEvent(Account account) {
    this.id = account.id();
    this.name = account.name();
    this.bankId = account.bank().id();
    this.organizationId = account.organizationId();
    this.createdAt = OffsetDateTime.now();
  }

  public UUID getBankId() {
    return bankId;
  }

  public void setBankId(UUID bankId) {
    this.bankId = bankId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
