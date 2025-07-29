package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.util.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CreateBankTest {

  @Mock private EventPublisher eventPublisher;

  @Mock private BankRepository bankRepository;

  @InjectMocks private CreateBank createBank;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("should to create a new bank")
  void shouldCreateNewBank() {
    var organizationId = UUID.randomUUID();
    var input = TestUtil.createBankInput(organizationId);

    var createdBank = TestUtil.createBank(organizationId);
    when(bankRepository.create(input)).thenReturn(createdBank);

    var result = createBank.execute(input);

    assertNotNull(result);
    assertEquals(createdBank.getName(), result.getName());
    assertEquals(createdBank.getOrganizationId(), result.getOrganizationId());
    assertEquals(createdBank.getCurrency(), result.getCurrency());
    verify(eventPublisher, times(1)).publish(any());
  }

  @Test
  @DisplayName("should get a error when try to create a bank already exists")
  void shouldNotCreateBankWhenExists() {
    var organizationId = UUID.randomUUID();
    var input = TestUtil.createBankInput(organizationId);

    when(bankRepository.existsByName(input.organizationId(), input.name())).thenReturn(true);

    var exception = assertThrows(BusinessLogicException.class, () -> createBank.execute(input));

    assertEquals("bank already exists", exception.getMessage());
    verify(bankRepository, never()).create(any());
    verify(eventPublisher, never()).publish(any());
  }
}
