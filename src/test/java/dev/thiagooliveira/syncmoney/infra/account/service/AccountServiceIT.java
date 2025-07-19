package dev.thiagooliveira.syncmoney.infra.account.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceIT extends IntegrationTest {

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private AccountService accountService;

  private Organization organization;

  @BeforeEach
  void setUp() {
    this.organization =
        this.organizationRepository.save(createOrganizationEntity()).toOrganization();
  }

  @Test
  void createBank() {
    var bank = this.accountService.createBank(createBankInput(organization.id()));
    assertNotNull(bank);
    assertNotNull(bank.id());
  }

  @Test
  void createAccount() {
    var bank = this.accountService.createBank(createBankInput(organization.id()));
    var account =
        this.accountService.createAccount(createAccountInput(organization.id(), bank.id()));
    assertNotNull(account);
    assertNotNull(account.id());
    assertEquals(bank.id(), account.bank().id());
  }
}
