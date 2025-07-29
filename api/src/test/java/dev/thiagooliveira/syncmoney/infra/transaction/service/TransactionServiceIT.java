package dev.thiagooliveira.syncmoney.infra.transaction.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.syncmoney.infra.IntegrationTest;

class TransactionServiceIT extends IntegrationTest {
  /*
  @Autowired private TransactionServiceProxy transactionService;

  @Autowired private OrganizationJpaRepository organizationJpaRepository;

  @Autowired private BankJpaRepository bankJpaRepository;

  @Autowired private CategoryJpaRepository categoryJpaRepository;

  @Autowired private AccountJpaRepository accountJpaRepository;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should get transaction page")
  void shouldGetTransactionPage() {
    var org = this.organizationJpaRepository.save(createOrganizationEntity());
    var bank =
        this.bankJpaRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountJpaRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryJpaRepository.save(createCategoryEntity(createCreditCategory(org.getId())));
    var transaction =
        this.transactionService.createDeposit(
            new CreateTransactionInput(
                account.getId(),
                org.getId(),
                OffsetDateTime.now(),
                "some transaction",
                category.getId(),
                BigDecimal.TEN));
    var page =
        this.transactionService.getByAccountId(org.getId(), account.getId(), YearMonth.now());
    assertNotNull(page);
  }

  @Test
  @DisplayName("should create a new transaction")
  void create() {
    var org = this.organizationJpaRepository.save(createOrganizationEntity());
    var bank =
        this.bankJpaRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountJpaRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryJpaRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

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
        .atMost(5, TimeUnit.SECONDS)
        .untilAsserted(
            () -> {
              var accountEntity = this.accountJpaRepository.findById(account.getId()).orElseThrow();
              assertEquals(0, accountEntity.getBalance().compareTo(BigDecimal.TEN));
            });
  }

  @Test
  @DisplayName("should create a new receivable")
  void createPayableReceivable() {
    var org = this.organizationJpaRepository.save(createOrganizationEntity());
    var bank =
        this.bankJpaRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountJpaRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryJpaRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

    var scheduledTransaction =
        this.transactionService.createReceivable(
            createPayableReceivableInput(
                org.getId(),
                account.getId(),
                category.getId(),
                BigDecimal.TEN,
                LocalDate.now(),
                Optional.empty()));
    assertNotNull(scheduledTransaction);
  }

  @Test
  @DisplayName("should get scheduled transaction list")
  void getScheduledTransactionList() {
    var org = this.organizationJpaRepository.save(createOrganizationEntity());
    var bank =
        this.bankJpaRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountJpaRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryJpaRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

    var scheduledTransaction =
        this.transactionService.createReceivable(
            createPayableReceivableInput(
                org.getId(),
                account.getId(),
                category.getId(),
                BigDecimal.TEN,
                LocalDate.now(),
                Optional.empty()));
    var list =
        this.transactionService.getByAccountId(org.getId(), account.getId(), YearMonth.now());
    assertNotNull(list);

    list =
        this.transactionService.getByAccountId(
            org.getId(), account.getId(), YearMonth.now().plusMonths(1));
    assertNotNull(list);
  }*/
}
