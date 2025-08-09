package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createAccount;
import static dev.thiagooliveira.syncmoney.util.TestUtil.createAccountInput;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.AccountRepository;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CreateAccountTest {

  @Mock private BankRepository bankRepository;

  @Mock private AccountRepository accountRepository;

  @InjectMocks private CreateAccount createAccount;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("should create a new account")
  void shouldCreateNewAccount() {
    // Arrange
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    CreateAccountInput input = createAccountInput(organizationId, bankId, userId);

    when(accountRepository.existsByName(eq(organizationId), eq(input.name()))).thenReturn(false);
    when(bankRepository.existsById(eq(organizationId), eq(bankId))).thenReturn(true);
    when(accountRepository.create(eq(input))).thenReturn(createAccount(input));

    // Act
    var account = createAccount.execute(input);

    // Assert
    assertNotNull(account);
    verify(accountRepository).create(eq(input));
  }

  @Test
  @DisplayName("should throw exception if account already exists")
  void shouldThrowExceptionIfAccountExists() {
    // Arrange
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    CreateAccountInput input = createAccountInput(organizationId, bankId, userId);

    when(accountRepository.existsByName(eq(organizationId), eq(input.name()))).thenReturn(true);

    // Act + Assert
    var exception = assertThrows(BusinessLogicException.class, () -> createAccount.execute(input));
    assertEquals("account with name '" + input.name() + "' already exists", exception.getMessage());
  }

  @Test
  @DisplayName("should throw exception if bank does not exist")
  void shouldThrowExceptionIfBankNotExist() {
    // Arrange
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    CreateAccountInput input = createAccountInput(organizationId, bankId, userId);

    when(accountRepository.existsByName(eq(organizationId), eq(input.name()))).thenReturn(false);
    when(bankRepository.existsById(eq(organizationId), eq(bankId))).thenReturn(false);

    // Act + Assert
    var exception = assertThrows(BusinessLogicException.class, () -> createAccount.execute(input));
    assertEquals("bank with id '" + bankId + "' not found", exception.getMessage());
  }
}
