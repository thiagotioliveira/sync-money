package dev.thiagooliveira.syncmoney.application.transaction.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.application.account.usecase.GetAccount;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.PayableReceivableCreatedEvent;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.PayableReceivablePort;
import dev.thiagooliveira.syncmoney.application.transaction.domain.port.TransactionPort;
import dev.thiagooliveira.syncmoney.infra.transaction.persistence.entity.TransactionEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateTransactionTest {

  private EventPublisher eventPublisher;
  private GetAccount getAccount;
  private GetCategory getCategory;
  private TransactionPort transactionPort;
  private PayableReceivablePort payableReceivablePort;
  private CreateTransaction createTransaction;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.getAccount = mock(GetAccount.class);
    this.getCategory = mock(GetCategory.class);
    this.transactionPort = mock(TransactionPort.class);
    this.payableReceivablePort = mock(PayableReceivablePort.class);
    this.createTransaction =
        new CreateTransaction(
            eventPublisher,
            this.getAccount,
            this.getCategory,
            transactionPort,
            this.payableReceivablePort);
  }

  @Test
  @DisplayName("should create a new payable")
  void shouldCreateANewPayable() {
    var organizationId = UUID.randomUUID();
    var bankId = UUID.randomUUID();
    var accountId = UUID.randomUUID();
    var categoryId = UUID.randomUUID();
    var input =
        createPayableReceivableInput(
            organizationId,
            accountId,
            categoryId,
            BigDecimal.TEN,
            LocalDate.now(),
            Optional.empty());
    when(this.getAccount.findById(organizationId, accountId))
        .thenReturn(Optional.of(createAccount(createAccountInput(organizationId, bankId))));
    when(this.getCategory.findById(organizationId, categoryId))
        .thenReturn(
            Optional.of(createCategory(organizationId, createDebitCategory(organizationId))));
    PayableReceivable payableReceivable =
        createPayableReceivable(organizationId, accountId, categoryId, input);
    when(this.payableReceivablePort.create(input)).thenReturn(payableReceivable);
    when(this.transactionPort.saveInstallments(any(List.class)))
        .thenReturn(
            payableReceivable.generateInstallments().stream()
                .map(TransactionEntity::from)
                .map(TransactionEntity::toTransaction)
                .map(Transaction::toInstallment)
                .toList());
    var scheduledTransaction = this.createTransaction.execute(input);
    assertNotNull(scheduledTransaction);
    verify(eventPublisher, times(1)).publish(any(PayableReceivableCreatedEvent.class));
  }
}
