package dev.thiagooliveira.syncmoney.core.transaction.domain.port.outcome;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.CreateTransferInput;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transfer;

public interface TransferRepository {

  public Transfer create(CreateTransferInput input);
}
