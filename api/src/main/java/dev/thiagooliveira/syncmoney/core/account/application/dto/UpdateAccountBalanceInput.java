package dev.thiagooliveira.syncmoney.core.account.application.dto;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.event.TransactionPaidEvent;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateAccountBalanceInput(
    UUID organizationId, UUID accountId, CategoryType categoryType, BigDecimal amount) {

  public UpdateAccountBalanceInput(TransactionPaidEvent event) {
    this(
        event.getOrganizationId(),
        event.getAccountId(),
        event.getCategoryType(),
        event.getAmount());
  }
}
