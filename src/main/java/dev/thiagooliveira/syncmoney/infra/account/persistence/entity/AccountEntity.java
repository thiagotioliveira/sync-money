package dev.thiagooliveira.syncmoney.infra.account.persistence.entity;

import static dev.thiagooliveira.syncmoney.infra.account.persistence.entity.BankEntity.mapToBank;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Account;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id private UUID id;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private String name;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "bank_id", nullable = false)
  private BankEntity bank;

  @Column(nullable = false)
  private BigDecimal balance;

  public AccountEntity() {}

  public AccountEntity(
      UUID id,
      OffsetDateTime createdAt,
      UUID organizationId,
      String name,
      BankEntity bank,
      BigDecimal balance) {
    this.id = id;
    this.createdAt = createdAt;
    this.organizationId = organizationId;
    this.createdAt = createdAt;
    this.name = name;
    this.bank = bank;
    this.balance = balance;
  }

  public static AccountEntity from(CreateAccountInput input) {
    AccountEntity entity = new AccountEntity();
    entity.id = UUID.randomUUID();
    entity.createdAt = OffsetDateTime.now();
    entity.organizationId = input.organizationId();
    entity.name = input.name();
    entity.bank = new BankEntity();
    entity.bank.setId(input.bankId());
    entity.balance = BigDecimal.ZERO;
    return entity;
  }

  public Account toAccount() {
    return new Account(id, name, mapToBank(this.bank), organizationId, balance, createdAt);
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
