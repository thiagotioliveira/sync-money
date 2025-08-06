package dev.thiagooliveira.syncmoney.infra.account.persistence.entity;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.BankCreatedEvent;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "banks")
public class BankEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private UUID organizationId;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Currency currency;

  public BankEntity() {}

  public BankEntity(UUID id, UUID organizationId, String name, Currency currency) {
    this.id = id;
    this.organizationId = organizationId;
    this.name = name;
    this.currency = currency;
  }

  public static BankEntity create(CreateBankInput input) {
    return new BankEntity(
        UUID.randomUUID(), input.organizationId(), input.name(), input.currency());
  }

  public static BankEntity restore(Bank bank) {
    BankEntity entity = new BankEntity();
    entity.id = bank.getId();
    entity.organizationId = bank.getOrganizationId();
    entity.name = bank.getName();
    entity.currency = bank.getCurrency();
    return entity;
  }

  public static Bank restore(BankEntity bankEntity) {
    return Bank.restore(
        bankEntity.getId(),
        bankEntity.getOrganizationId(),
        bankEntity.getName(),
        bankEntity.getCurrency());
  }

  public Bank toBank() {
    return Bank.restore(id, organizationId, name, currency);
  }

  public Bank toBankCreated() {
    var bank = Bank.restore(id, organizationId, name, currency);
    bank.registerEvent(
        new BankCreatedEvent(bank.getId(), bank.getName(), bank.getOrganizationId()));
    return bank;
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

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
