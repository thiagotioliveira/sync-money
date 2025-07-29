package dev.thiagooliveira.syncmoney.util;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.BankCreatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserCreatedEvent;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TestUtil {
  public static final String USER_NAME = "John Due";
  public static final String USER_EMAIL = "john.due@syncmoney.app";

  public static final String CATEGORY_CREDIT_NAME = "Salary";
  public static final String CATEGORY_DEBIT_NAME = "Electricity Tax";

  public static final String BANK_NAME = "Default Bank";
  public static final Currency BANK_CURRENCY = Currency.EUR;

  public static final String ACCOUNT_NAME = "Account Bank";

  public static CreateUserInput createUserInput() {
    return new CreateUserInput(USER_EMAIL, USER_NAME, "some-password");
  }

  public static Organization createOrganization() {
    return Organization.create(USER_EMAIL);
  }

  public static UserAuthenticated userAuthenticated(User user) {
    return new UserAuthenticated(user);
  }

  public static User createUser(Organization organization) {
    User user = User.create(USER_EMAIL, USER_NAME, organization);
    user.registerEvent(new UserCreatedEvent(user));
    return user;
  }

  public static User getUser(Organization organization) {
    User user = User.create(USER_EMAIL, USER_NAME, organization);
    return user;
  }

  public static UserWithPassword getUserWithPassword(Organization organization) {
    User user = User.create(USER_EMAIL, USER_NAME, organization);
    return UserWithPassword.restore(
        user.getId(),
        user.getEmail(),
        user.getName(),
        "some-pass",
        user.getCreatedAt(),
        user.getOrganizationId());
  }

  public static CreateBankInput createBankInput(UUID organizationId) {
    return new CreateBankInput(organizationId, BANK_NAME, BANK_CURRENCY);
  }

  public static Bank createBank(UUID organizationId) {
    var bank = Bank.restore(UUID.randomUUID(), organizationId, BANK_NAME, BANK_CURRENCY);
    bank.registerEvent(new BankCreatedEvent(bank));
    return bank;
  }

  public static Bank getBank(UUID organizationId) {
    var bank = Bank.restore(UUID.randomUUID(), organizationId, BANK_NAME, BANK_CURRENCY);
    return bank;
  }

  public static CreateAccountInput createAccountInput(
      UUID organizationId, UUID bankId, UUID userId) {
    return new CreateAccountInput(ACCOUNT_NAME, bankId, organizationId, userId, BigDecimal.ZERO);
  }

  public static Account createAccount(CreateAccountInput input) {
    Account account =
        Account.restore(
            UUID.randomUUID(),
            input.name(),
            input.bankId(),
            input.organizationId(),
            BigDecimal.ZERO,
            OffsetDateTime.now(),
            0L);
    account.registerEvent(new AccountCreatedEvent(account, input.userId(), input.initialBalance()));
    return account;
  }
}
