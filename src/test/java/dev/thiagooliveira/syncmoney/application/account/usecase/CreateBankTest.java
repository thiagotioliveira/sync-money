package dev.thiagooliveira.syncmoney.application.account.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createBank;
import static dev.thiagooliveira.syncmoney.util.TestUtil.createBankInput;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.event.BankCreatedEvent;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.port.BankPort;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateBankTest {

  private EventPublisher eventPublisher;
  private BankPort bankPort;

  private CreateBank createBank;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.bankPort = mock(BankPort.class);
    this.createBank = new CreateBank(eventPublisher, bankPort);
  }

  @Test
  @DisplayName("should to create a new bank")
  void execute() {
    var organizationId = UUID.randomUUID();
    var input = createBankInput(organizationId);
    when(this.bankPort.existsByName(eq(organizationId), eq(input.name()))).thenReturn(false);
    Bank bankExpected = createBank(organizationId, UUID.randomUUID());
    when(this.bankPort.create(eq(input))).thenReturn(bankExpected);
    var bank = this.createBank.execute(input);
    assertNotNull(bank);
    assertEquals(bankExpected, bank);
    verify(this.eventPublisher, times(1)).publish(any(BankCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because the bank already exists")
  void executeWithExistingBank() {
    var organizationId = UUID.randomUUID();
    var input = createBankInput(organizationId);
    when(this.bankPort.existsByName(eq(organizationId), eq(input.name()))).thenReturn(true);
    var excepiton =
        assertThrows(BusinessLogicException.class, () -> this.createBank.execute(input));
    assertEquals("bank already exists", excepiton.getMessage());
  }
}
