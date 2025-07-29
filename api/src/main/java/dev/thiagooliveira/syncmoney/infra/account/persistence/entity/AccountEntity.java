package dev.thiagooliveira.syncmoney.infra.account.persistence.entity;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountBalanceUpdatedEvent;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountCreatedEvent;
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

  @Version private Long version;

  public AccountEntity() {}

  public static AccountEntity create(CreateAccountInput input) {
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

  public static AccountEntity restore(Account account) {
    AccountEntity entity = new AccountEntity();
    entity.id = account.getId();
    entity.createdAt = account.getCreatedAt();
    entity.organizationId = account.getOrganizationId();
    entity.name = account.getName();
    entity.bank = new BankEntity();
    entity.bank.setId(account.getBankId());
    entity.balance = account.getBalance();
    entity.version = account.getVersion();
    return entity;
  }

  public Account toAccount() {
    return Account.restore(
        id, name, this.bank.getId(), organizationId, balance, createdAt, version);
  }

  public Account toAccountCreated(UUID userId, BigDecimal initialBalance) {
    var account = toAccount();
    account.registerEvent(
        new AccountCreatedEvent(
            account.getId(),
            account.getName(),
            account.getOrganizationId(),
            userId,
            account.getBankId(),
            initialBalance,
            account.getCreatedAt()));
    return account;
  }

  public Account toAccountUpdated() {
    var account = toAccount();
    account.registerEvent(
        new AccountBalanceUpdatedEvent(
            account.getId(), account.getOrganizationId(), account.getBalance()));
    return account;
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

  public BankEntity getBank() {
    return bank;
  }

  public void setBank(BankEntity bank) {
    this.bank = bank;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
