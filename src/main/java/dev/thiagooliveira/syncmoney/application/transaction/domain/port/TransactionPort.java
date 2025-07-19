package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;

public interface TransactionPort {

  Transaction create(CreateTransactionInput input);
}
