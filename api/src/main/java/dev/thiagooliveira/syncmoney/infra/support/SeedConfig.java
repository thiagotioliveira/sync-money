package dev.thiagooliveira.syncmoney.infra.support;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateCategoryInput;
import dev.thiagooliveira.syncmoney.core.transaction.application.service.CategoryService;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.core.user.application.usecase.CreateUser;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
public class SeedConfig {

  @Bean
  public UserContext userContext() {
    return new UserContext();
  }

  @Bean
  @Transactional
  public CommandLineRunner init(
      AppSeedProperties appSeedProperties,
      UserContext userContext,
      UserService userService,
      OrganizationJpaRepository organizationJpaRepository,
      CreateUser createUser,
      AccountService accountService,
      CategoryService categoryService) {
    return (args) -> {
      if (organizationJpaRepository.count() == 0) {
        var userOwner = appSeedProperties.getUsers().stream().findFirst().orElseThrow();
        var user =
            userService.create(
                new CreateUserInput(
                    userOwner.getEmail(), userOwner.getName(), userOwner.getPassword()));
        var organizationId = user.getOrganizationId();
        var otherUsers = new HashSet<>(appSeedProperties.getUsers());
        otherUsers.remove(userOwner);
        otherUsers.forEach(
            u ->
                createUser.execute(
                    new CreateUserInOrganizationInput(
                        u.getEmail(), u.getName(), u.getPassword(), organizationId)));

        appSeedProperties
            .getCategories()
            .forEach(
                c -> {
                  categoryService.create(
                      new CreateCategoryInput(
                          organizationId, c.getName(), CategoryType.valueOf(c.getType())));
                });

        appSeedProperties
            .getBanks()
            .forEach(
                b -> {
                  var bank =
                      accountService.createBank(
                          new CreateBankInput(
                              organizationId, b.getName(), Currency.valueOf(b.getCurrency())));
                  b.getAccounts()
                      .forEach(
                          a -> {
                            accountService.createAccount(
                                new CreateAccountInput(
                                    a.getName(),
                                    bank.getId(),
                                    organizationId,
                                    user.getId(),
                                    a.getBalance()));
                          });
                });

        System.out.println("Organization Created: " + organizationId);
        userContext.setOrganizationId(organizationId);
        userContext.setUserId(user.getId());
      } else {
        var organization = organizationJpaRepository.findAll().get(0);
        var user = userService.getAll(organization.getId()).get(0);
        System.out.println("Organization Id: " + organization.getId());
        userContext.setOrganizationId(organization.getId());
        userContext.setUserId(user.getId());
      }
    };
  }
}
