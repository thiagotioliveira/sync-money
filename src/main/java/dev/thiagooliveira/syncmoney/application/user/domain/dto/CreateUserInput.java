package dev.thiagooliveira.syncmoney.application.user.domain.dto;

public record CreateUserInput(String email, String name, String password) {

  public CreateOrganizationInput toCreateOrganizationInput() {
    return new CreateOrganizationInput(this.email);
  }
}
