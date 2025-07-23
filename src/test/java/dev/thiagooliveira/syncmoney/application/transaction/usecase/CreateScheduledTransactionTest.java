package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.ScheduledTransactionCreatedEvent;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.ScheduledTransactionType;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.ScheduledTransactionPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateScheduledTransactionTest {

  private EventPublisher eventPublisher;
  private GetAccount getAccount;
  private GetCategory getCategory;
  private ScheduledTransactionPort scheduledTransactionPort;
  private CreateScheduledTransaction createScheduledTransaction;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.getAccount = mock(GetAccount.class);
    this.getCategory = mock(GetCategory.class);
    this.scheduledTransactionPort = mock(ScheduledTransactionPort.class);
    this.createScheduledTransaction =
        new CreateScheduledTransaction(
            eventPublisher, this.getAccount, this.getCategory, this.scheduledTransactionPort);
  }

  @Test
  @DisplayName("should create a new scheduled transaction")
  void shouldCreateNewScheduledTransaction() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            ScheduledTransactionType.PAYABLE,
            LocalDate.now(),
            Optional.empty());
    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    when(this.scheduledTransactionPort.createTemplate(input))
        .thenReturn(createScheduledTransactionTemplate(accountId, categoryId, input));
    var scheduledTransaction = this.createScheduledTransaction.execute(input);
    assertNotNull(scheduledTransaction);
    verify(eventPublisher, times(1)).publish(any(ScheduledTransactionCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because category is credit and type is payable")
  void shouldGetErrorBecauseCategoryIsCreditAndTypeIsPayable() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            ScheduledTransactionType.PAYABLE,
            LocalDate.now(),
            Optional.empty());

    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createCreditCategory(organizationId))));
    var exception =
        assertThrows(
            BusinessLogicException.class, () -> this.createScheduledTransaction.execute(input));
    assertNotNull(exception);
    assertEquals("payable credit transaction is not supported", exception.getMessage());
    verify(eventPublisher, times(0)).publish(any(ScheduledTransactionCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because category is debit and type is receivable")
  void shouldGetErrorBecauseCategoryIsDebitAndTypeIsReceivable() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            ScheduledTransactionType.RECEIVABLE,
            LocalDate.now(),
            Optional.empty());

    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    var exception =
        assertThrows(
            BusinessLogicException.class, () -> this.createScheduledTransaction.execute(input));
    assertNotNull(exception);
    assertEquals("receivable debit transaction is not supported", exception.getMessage());
    verify(eventPublisher, times(0)).publish(any(ScheduledTransactionCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because start date is older then today")
  void shouldGetErrorBecauseStartDateIsOlderThenToday() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            ScheduledTransactionType.PAYABLE,
            LocalDate.now().minusDays(1),
            Optional.empty());

    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    var exception =
        assertThrows(
            BusinessLogicException.class, () -> this.createScheduledTransaction.execute(input));
    assertNotNull(exception);
    assertEquals("start date needs to be after today", exception.getMessage());
    verify(eventPublisher, times(0)).publish(any(ScheduledTransactionCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because installment total is less or equals zero")
  void shouldGetErrorBecauseInstallmentTotalIsLessOrEqualsZero() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            ScheduledTransactionType.PAYABLE,
            LocalDate.now(),
            Optional.of(-1));

    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    var exception =
        assertThrows(
            BusinessLogicException.class, () -> this.createScheduledTransaction.execute(input));
    assertNotNull(exception);
    assertEquals("installment total must be greater than zero", exception.getMessage());
    verify(eventPublisher, times(0)).publish(any(ScheduledTransactionCreatedEvent.class));
  }

  @Test
  @DisplayName("should get a error because amount is negative")
  void shouldGetErrorBecauseAmountIsNegative() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createScheduledTransactionInput(
            organizationId,
            accountId,
            categoryId,
            new BigDecimal("-1"),
            ScheduledTransactionType.PAYABLE,
            LocalDate.now(),
            Optional.empty());
    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    var exception =
        assertThrows(
            BusinessLogicException.class, () -> this.createScheduledTransaction.execute(input));
    assertNotNull(exception);
    assertEquals("amount must be greater than zero", exception.getMessage());
    verify(eventPublisher, times(0)).publish(any(ScheduledTransactionCreatedEvent.class));
  }
}
