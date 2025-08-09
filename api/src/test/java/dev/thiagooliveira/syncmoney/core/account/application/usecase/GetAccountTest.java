package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.util.TestUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetAccountTest {

  @Mock private AccountRepository accountRepository;

  @Mock private BankRepository bankRepository;

  private GetAccount getAccount;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    getAccount = new GetAccount(accountRepository, bankRepository);
  }

  @Test
  void existsById() {
    UUID organizationId = UUID.randomUUID();
    UUID accountId = UUID.randomUUID();
    when(accountRepository.existsById(organizationId, accountId)).thenReturn(true);

    boolean result = getAccount.existsById(organizationId, accountId);

    assertTrue(result);
    verify(accountRepository).existsById(organizationId, accountId);
  }

  @Test
  void getById() {
    UUID organizationId = UUID.randomUUID();
    UUID accountId = UUID.randomUUID();
    Bank bank = TestUtil.createBank(organizationId);
    Account account =
        Account.restore(accountId, "Account 1", bank.getId(), organizationId, null, null, null);

    when(accountRepository.getById(organizationId, accountId)).thenReturn(Optional.of(account));
    when(bankRepository.getById(organizationId, bank.getId())).thenReturn(Optional.of(bank));

    var result = getAccount.getById(organizationId, accountId);

    assertTrue(result.isPresent());
    assertEquals(account.getId(), result.get().id());
    assertEquals(bank.getId(), result.get().bank().getId());
    verify(accountRepository).getById(organizationId, accountId);
    verify(bankRepository).getById(organizationId, bank.getId());
  }

  @Test
  void getAll() {
    UUID organizationId = UUID.randomUUID();
    Account account1 =
        Account.restore(
            UUID.randomUUID(), "Account 1", UUID.randomUUID(), organizationId, null, null, null);
    Account account2 =
        Account.restore(
            UUID.randomUUID(), "Account 2", UUID.randomUUID(), organizationId, null, null, null);
    when(accountRepository.getAll(organizationId)).thenReturn(List.of(account1, account2));

    var results = getAccount.getAll(organizationId);

    assertEquals(2, results.size());
    assertTrue(results.contains(account1));
    assertTrue(results.contains(account2));
    verify(accountRepository).getAll(organizationId);
  }
}
