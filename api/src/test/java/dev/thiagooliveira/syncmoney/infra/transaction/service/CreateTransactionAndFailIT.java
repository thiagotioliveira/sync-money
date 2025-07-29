package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountJpaRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankJpaRepository;
import dev.thiagooliveira.syncmoney.infra.account.service.AccountServiceProxy;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryJpaRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class CreateTransactionAndFailIT extends IntegrationTest {
  @Autowired private TransactionServiceProxy transactionService;

  @Autowired private OrganizationJpaRepository organizationJpaRepository;

  @Autowired private BankJpaRepository bankJpaRepository;

  @Autowired private CategoryJpaRepository categoryJpaRepository;

  @Autowired private AccountJpaRepository accountJpaRepository;

  @MockitoBean private AccountServiceProxy accountService;

  @BeforeEach
  void setUp() {}
  /*
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
        this.transactionService.createDeposit(
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
  */
}
