package dev.thiagooliveira.syncmoney.core.account.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.AggregateRoot;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Account extends AggregateRoot {
  private UUID id;
  private String name;
  private UUID bankId;
  private UUID organizationId;
  private BigDecimal balance;
  private OffsetDateTime createdAt;
  private Long version;

  private Account() {}

  private Account(
      UUID id,
      String name,
      UUID bankId,
      UUID organizationId,
      BigDecimal balance,
      OffsetDateTime createdAt,
      Long version) {
    this.id = id;
    this.name = name;
    this.bankId = bankId;
    this.organizationId = organizationId;
    this.balance = balance;
    this.createdAt = createdAt;
    this.version = version;
  }

  public static Account restore(
      UUID id,
      String name,
      UUID bankId,
      UUID organizationId,
      BigDecimal balance,
      OffsetDateTime createdAt,
      Long version) {
    return new Account(id, name, bankId, organizationId, balance, createdAt, version);
  }

  public Account withdraw(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
    return this;
  }

  public Account deposit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
    return this;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public UUID getBankId() {
    return bankId;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public Long getVersion() {
    return version;
  }
}
