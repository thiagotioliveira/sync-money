package dev.thiagooliveira.syncmoney.infra;

import dev.thiagooliveira.syncmoney.TestcontainersConfiguration;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountJpaRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankJpaRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.AccountSummaryJpaRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryJpaRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.PayableReceivableJpaRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionJpaRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationJpaRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationTest {
  @Autowired private OrganizationJpaRepository organizationJpaRepository;
  @Autowired private UserJpaRepository userJpaRepository;
  @Autowired private CategoryJpaRepository categoryJpaRepository;
  @Autowired private BankJpaRepository bankJpaRepository;
  @Autowired private AccountJpaRepository accountJpaRepository;
  @Autowired private TransactionJpaRepository transactionJpaRepository;
  @Autowired private PayableReceivableJpaRepository payableReceivableJpaRepository;
  @Autowired private AccountSummaryJpaRepository accountSummaryJpaRepository;

  @AfterEach
  void tearDown() {
    this.accountSummaryJpaRepository.deleteAll();
    this.transactionJpaRepository.deleteAll();
    this.payableReceivableJpaRepository.deleteAll();
    this.accountJpaRepository.deleteAll();
    this.bankJpaRepository.deleteAll();
    this.categoryJpaRepository.deleteAll();
    this.userJpaRepository.deleteAll();
    this.organizationJpaRepository.deleteAll();
  }
}
