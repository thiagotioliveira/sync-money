package dev.thiagooliveira.syncmoney.core.user.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;

public record CreateUserInput(String email, String name, String password) {

  public CreateOrganizationInput toCreateOrganizationInput() {
    return new CreateOrganizationInput(this.email);
  }

  public CreateUserInput withCredentialEncoder(CredentialEncoder encoder) {
    return new CreateUserInput(this.email, this.name, encoder.encode(this.password));
  }
}
