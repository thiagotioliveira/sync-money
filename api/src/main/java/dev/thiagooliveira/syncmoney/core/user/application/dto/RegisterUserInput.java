package dev.thiagooliveira.syncmoney.core.user.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;

public record RegisterUserInput(String email, String name, String password) {

  public CreateOrganizationInput toCreateOrganizationInput() {
    return new CreateOrganizationInput(this.email);
  }

  public RegisterUserInput withCredentialEncoder(CredentialEncoder encoder) {
    return new RegisterUserInput(this.email, this.name, encoder.encode(this.password));
  }
}
