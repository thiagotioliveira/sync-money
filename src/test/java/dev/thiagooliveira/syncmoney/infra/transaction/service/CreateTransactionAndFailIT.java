package dev.thiagooliveira.syncmoney.infra.transaction.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static dev.thiagooliveira.syncmoney.util.TestUtil.createCreditCategory;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankRepository;
import dev.thiagooliveira.syncmoney.infra.account.service.AccountService;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class CreateTransactionAndFailIT extends IntegrationTest {
  @Autowired private TransactionService transactionService;

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private BankRepository bankRepository;

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private AccountRepository accountRepository;

  @MockitoBean private AccountService accountService;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should create a new transaction but get error when try to update account balance")
  void create() {
    doThrow(new RuntimeException()).when(this.accountService).updateAccountBalance(any());
    var org = this.organizationRepository.save(createOrganizationEntity());
    var bank =
        this.bankRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

    var transaction =
        this.transactionService.create(
            new CreateTransactionInput(
                account.getId(),
                org.getId(),
                OffsetDateTime.now(),
                "some transaction",
                category.getId(),
                BigDecimal.TEN));
    assertNotNull(transaction);

    await()
        .pollDelay(2, TimeUnit.SECONDS)
        .atMost(3, TimeUnit.SECONDS)
        .untilAsserted(
            () -> {
              var accountEntity = this.accountRepository.findById(account.getId()).orElseThrow();
              assertEquals(0, accountEntity.getBalance().compareTo(BigDecimal.ZERO));
            });
  }
}
