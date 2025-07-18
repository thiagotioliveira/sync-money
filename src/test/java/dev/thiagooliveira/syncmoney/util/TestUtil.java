package dev.thiagooliveira.syncmoney.util;

import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;

public class TestUtil {
  public static final String USER_NAME = "John Due";
  public static final String USER_EMAIL = "john.due@syncmoney.app";

  public static CreateUserInput createUserInput() {
    return new CreateUserInput(USER_EMAIL, USER_NAME, "some-password");
  }

  public static User createUser(Organization organization) {
    return User.create(USER_EMAIL, USER_NAME, organization);
  }

  public static Organization createOrganization() {
    return Organization.create(USER_EMAIL);
  }
}
