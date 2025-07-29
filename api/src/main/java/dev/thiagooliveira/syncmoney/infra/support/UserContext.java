package dev.thiagooliveira.syncmoney.infra.support;

import java.util.UUID;

public class UserContext {
  private UUID organizationId;
  private UUID userId;

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
