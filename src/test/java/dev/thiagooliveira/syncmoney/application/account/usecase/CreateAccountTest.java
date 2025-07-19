package dev.thiagooliveira.syncmoney.application.account.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.event.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.application.account.domain.port.AccountPort;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateAccountTest {

  private EventPublisher eventPublisher;
  private GetBank getBank;
  private AccountPort accountPort;

  private CreateAccount createAccount;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.getBank = mock(GetBank.class);
    this.accountPort = mock(AccountPort.class);
    this.createAccount = new CreateAccount(eventPublisher, getBank, accountPort);
  }

  @Test
  @DisplayName("should to create a new account")
  void execute() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var input = createAccountInput(organizationId, bankId);
    when(this.accountPort.existsByName(organizationId, input.name())).thenReturn(false);
    when(this.getBank.byId(organizationId, bankId))
        .thenReturn(Optional.of(createBank(organizationId)));
    Account accountExpected = createAccount(input);
    when(this.accountPort.create(input)).thenReturn(accountExpected);
    var account = this.createAccount.execute(input);
    assertNotNull(account);
    assertEquals(accountExpected, account);
    verify(eventPublisher, times(1)).publish(any(AccountCreatedEvent.class));
  }
}
