package dev.thiagooliveira.syncmoney.application.transaction.domain.port;

import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Page;
import dev.thiagooliveira.syncmoney.application.support.page.domain.dto.Pageable;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.syncmoney.application.transaction.domain.model.Transaction;
import java.util.UUID;

public interface TransactionPort {

  Transaction create(CreateTransactionInput input);

  Page<TransactionEnriched> findByAccountId(UUID accountId, Pageable pageable);
}
