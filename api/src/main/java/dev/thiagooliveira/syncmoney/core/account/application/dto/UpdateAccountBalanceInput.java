package dev.thiagooliveira.syncmoney.core.account.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CategoryType;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionPaidEvent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateAccountBalanceInput(
    UUID organizationId,
    UUID accountId,
    CategoryType categoryType,
    BigDecimal amount,
    UUID userId,
    OffsetDateTime dateTime) {

  public UpdateAccountBalanceInput(TransactionPaidEvent event) {
    this(
        event.getOrganizationId(),
        event.getAccountId(),
        CategoryType.valueOf(event.getCategoryType().name()),
        event.getAmount(),
        event.getUserId(),
        event.getDateTime());
  }
}
