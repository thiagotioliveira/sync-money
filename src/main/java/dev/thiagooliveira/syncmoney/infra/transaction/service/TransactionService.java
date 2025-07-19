package dev.thiagooliveira.syncmoney.infra.transaction.service;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.application.transaction.usecase.CreateTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

  private final CreateTransaction createTransaction;

  public TransactionService(CreateTransaction createTransaction) {
    this.createTransaction = createTransaction;
  }

  @Transactional
  public Transaction create(CreateTransactionInput createTransactionInput) {
    return this.createTransaction.create(createTransactionInput);
  }
}
