package dev.thiagooliveira.syncmoney.util;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.application.category.domain.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.category.domain.model.Category;
import dev.thiagooliveira.syncmoney.application.category.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateScheduledTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Frequency;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionTemplate;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionType;
import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.AccountEntity;
import dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity;
import dev.thiagooliveira.syncmoney.infra.category.persistence.entity.CategoryEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  public static CategoryEntity createCategoryEntity(CreateCategoryInput input) {
    var entity = new CategoryEntity();
    entity.setId(UUID.randomUUID());
    entity.setOrganizationId(input.organizationId());
    entity.setName(input.name());
    entity.setType(input.type());
    return entity;
  }

  public static CreateCategoryInput createDebitCategory(UUID organizationId) {
    return new CreateCategoryInput(organizationId, CATEGORY_DEBIT_NAME, CategoryType.DEBIT);
  }

  public static CreateBankInput createBankInput(UUID organizationId) {
    return new CreateBankInput(organizationId, BANK_NAME, BANK_CURRENCY);
  }

  public static Bank createBank(UUID organizationId, UUID bankId) {
    return new Bank(bankId, organizationId, BANK_NAME, BANK_CURRENCY);
  }

  public static BankEntity createBankEntity(Bank bank) {
    var entity = new BankEntity();
    entity.setId(bank.id());
    entity.setOrganizationId(bank.organizationId());
    entity.setName(bank.name());
    entity.setCurrency(bank.currency());
    return entity;
  }

  public static CreateAccountInput createAccountInput(UUID organizationId, UUID bankId) {
    return new CreateAccountInput(ACCOUNT_NAME, bankId, organizationId);
  }

  public static Account createAccount(CreateAccountInput input) {
    return new Account(
        UUID.randomUUID(),
        input.name(),
        createBank(input.organizationId(), input.bankId()),
        input.organizationId(),
        BigDecimal.ZERO,
        OffsetDateTime.now(),
        null);
  }

  public static AccountEntity createAccountEntity(Account account) {
    var entity = new AccountEntity();
    entity.setId(account.id());
    entity.setName(account.name());
    entity.setBalance(account.balance());
    entity.setOrganizationId(account.organizationId());
    entity.setCreatedAt(account.createdAt());
    entity.setBank(BankEntity.from(account.bank()));
    return entity;
  }

  public static CreateScheduledTransactionInput createScheduledTransactionInput(
      UUID organizationId,
      UUID accountId,
      UUID categoryId,
      BigDecimal amount,
      ScheduledTransactionType type,
      LocalDate startDate,
      Optional<Integer> installmentTotal) {
    return new CreateScheduledTransactionInput(
        organizationId,
        accountId,
        categoryId,
        type,
        "some scheduled transaction",
        amount,
        startDate,
        true,
        installmentTotal);
  }

  public static ScheduledTransactionTemplate createScheduledTransactionTemplate(
      UUID accountId, UUID categoryId, CreateScheduledTransactionInput input) {
    return new ScheduledTransactionTemplate(
        UUID.randomUUID(),
        accountId,
        categoryId,
        input.description(),
        input.amount(),
        input.type(),
        input.startDate(),
        null,
        true,
        input.recurring(),
        Frequency.MONTHLY,
        input.installmentTotal());
  }
}
