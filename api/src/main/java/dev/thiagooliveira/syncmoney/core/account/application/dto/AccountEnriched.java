package dev.thiagooliveira.syncmoney.core.account.application.dto;

import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AccountEnriched(
    UUID id,
    String name,
    Bank bank,
    UUID organizationId,
    BigDecimal balance,
    OffsetDateTime createdAt,
    Long version) {
  public AccountEnriched(Account account, Bank bank) {
    this(
        account.getId(),
        account.getName(),
        bank,
        account.getOrganizationId(),
        account.getBalance(),
        account.getCreatedAt(),
        account.getVersion());
  }
}
