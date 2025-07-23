package dev.thiagooliveira.syncmoney.application.account.domain.dto;

import dev.thiagooliveira.syncmoney.application.transaction.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.TransactionCreatedEvent;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateAccountBalanceInput(
    UUID organizationId, UUID accountId, CategoryType categoryType, BigDecimal amount) {

  public UpdateAccountBalanceInput(TransactionCreatedEvent event) {
    this(
        event.getOrganizationId(),
        event.getAccountId(),
        event.getCategoryType(),
        event.getAmount());
  }
}
