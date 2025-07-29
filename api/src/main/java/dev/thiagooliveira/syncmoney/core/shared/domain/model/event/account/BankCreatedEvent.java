package dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.util.UUID;

public class BankCreatedEvent implements Event {
  private UUID id;
  private UUID organizationId;
  private String name;

  public BankCreatedEvent(UUID id, String name, UUID organizationId) {
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
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
}
