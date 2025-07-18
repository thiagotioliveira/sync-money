package dev.thiagooliveira.syncmoney.util;

import dev.thiagooliveira.syncmoney.application.category.domain.CategoryType;
import dev.thiagooliveira.syncmoney.application.category.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.application.user.domain.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.syncmoney.infra.user.persistence.entity.UserEntity;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TestUtil {
  public static final String USER_NAME = "John Due";
  public static final String USER_EMAIL = "john.due@syncmoney.app";

  public static final String CATEGORY_CREDIT_NAME = "Salary";
  public static final String CATEGORY_DEBIT_NAME = "Electricity Tax";

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

  public static CreateCategoryInput createDebitCategory(UUID organizationId) {
    return new CreateCategoryInput(organizationId, CATEGORY_DEBIT_NAME, CategoryType.DEBIT);
  }
}
