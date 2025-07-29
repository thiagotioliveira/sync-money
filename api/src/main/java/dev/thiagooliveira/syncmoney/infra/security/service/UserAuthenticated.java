package dev.thiagooliveira.syncmoney.infra.security.service;

import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import java.security.Principal;
import java.util.UUID;

public class UserAuthenticated implements Principal {
  private final UUID id;
  private final UUID organizationId;
  private final String name;
  private final String email;

  public UserAuthenticated(User user) {
    this.id = user.getId();
    this.organizationId = user.getOrganizationId();
    this.name = user.getName();
    this.email = user.getEmail();
  }

  public UserAuthenticated(UserWithPassword user) {
    this.id = user.getId();
    this.organizationId = user.getOrganizationId();
    this.name = user.getName();
    this.email = user.getEmail();
  }

  public String getEmail() {
    return email;
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
