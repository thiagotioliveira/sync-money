package dev.thiagooliveira.syncmoney.util;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
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

  public static UserEntity createUserEntity(OrganizationEntity organizationEntity) {
    var entity = new UserEntity();
    entity.setEmail(USER_EMAIL);
    entity.setName(USER_NAME);
    entity.setPassword("some-password");
    entity.setCreatedAt(organizationEntity.getCreatedAt());
    entity.setOrganizationId(organizationEntity.getId());
    entity.setId(UUID.randomUUID());
    return entity;
  }

  public static User createUser(Organization organization) {
    return User.create(USER_EMAIL, USER_NAME, organization);
  }

  public static OrganizationEntity createOrganizationEntity() {
    return new OrganizationEntity(UUID.randomUUID(), OffsetDateTime.now(), USER_EMAIL);
  }

  public static Organization createOrganization() {
    return Organization.create(USER_EMAIL);
  }

  public static CreateCategoryInput createCreditCategory(UUID organizationId) {
    return new CreateCategoryInput(organizationId, CATEGORY_CREDIT_NAME, CategoryType.CREDIT);
  }

  public static Category createCategory(UUID organizationId, CreateCategoryInput input) {
    return new Category(UUID.randomUUID(), Optional.of(organizationId), input.name(), input.type());
  }

  public static CreateCategoryInput createDebitCategory(UUID organizationId) {
    return new CreateCategoryInput(organizationId, CATEGORY_DEBIT_NAME, CategoryType.DEBIT);
  }

  public static CreateBankInput createBankInput(UUID organizationId) {
    return new CreateBankInput(organizationId, BANK_NAME, BANK_CURRENCY);
  }

  public static Bank createBank(UUID organizationId) {
    return new Bank(UUID.randomUUID(), organizationId, BANK_NAME, BANK_CURRENCY);
  }

  public static CreateAccountInput createAccountInput(UUID organizationId, UUID bankId) {
    return new CreateAccountInput(ACCOUNT_NAME, bankId, organizationId);
  }

  public static Account createAccount(CreateAccountInput input) {
    return new Account(
        UUID.randomUUID(),
        input.name(),
        createBank(input.organizationId()),
        input.organizationId(),
        BigDecimal.ZERO,
        OffsetDateTime.now());
  }
}
