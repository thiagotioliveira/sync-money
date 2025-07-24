package dev.thiagooliveira.syncmoney.infra;

import dev.thiagooliveira.syncmoney.TestcontainersConfiguration;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.AccountRepository;
import dev.thiagooliveira.syncmoney.infra.account.persistence.repository.BankRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.CategoryRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.PayableReceivableRepository;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.repository.TransactionRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class IntegrationTest {
  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private BankRepository bankRepository;
  @Autowired private AccountRepository accountRepository;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private PayableReceivableRepository payableReceivableRepository;

  @AfterEach
  void tearDown() {
    this.transactionRepository.deleteAll();
    this.payableReceivableRepository.deleteAll();
    this.accountRepository.deleteAll();
    this.bankRepository.deleteAll();
    this.categoryRepository.deleteAll();
    this.userRepository.deleteAll();
    this.organizationRepository.deleteAll();
  }
}
