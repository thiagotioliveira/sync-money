package dev.thiagooliveira.syncmoney.infra.transaction.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransactionServiceIT extends IntegrationTest {

  @Autowired private TransactionService transactionService;

  @Autowired private OrganizationRepository organizationRepository;

  @Autowired private BankRepository bankRepository;

  @Autowired private CategoryRepository categoryRepository;

  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should get transaction page")
  void shouldGetTransactionPage() {
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
    var page =
        this.transactionService.getByAccountId(org.getId(), account.getId(), Pageable.of(0, 10));
    assertNotNull(page);
  }

  @Test
  @DisplayName("should create a new transaction")
  void create() {
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
        .atMost(5, TimeUnit.SECONDS)
        .untilAsserted(
            () -> {
              var accountEntity = this.accountRepository.findById(account.getId()).orElseThrow();
              assertEquals(0, accountEntity.getBalance().compareTo(BigDecimal.TEN));
            });
  }

  @Test
  @DisplayName("should create a new receivable")
  void createPayableReceivable() {
    var org = this.organizationRepository.save(createOrganizationEntity());
    var bank =
        this.bankRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

    var scheduledTransaction =
        this.transactionService.createPayableReceivable(
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
    var org = this.organizationRepository.save(createOrganizationEntity());
    var bank =
        this.bankRepository.save(createBankEntity(createBank(org.getId(), UUID.randomUUID())));
    var account =
        this.accountRepository.save(
            createAccountEntity(createAccount(createAccountInput(org.getId(), bank.getId()))));
    var category =
        this.categoryRepository.save(createCategoryEntity(createCreditCategory(org.getId())));

    var scheduledTransaction =
        this.transactionService.createPayableReceivable(
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
  }
}
