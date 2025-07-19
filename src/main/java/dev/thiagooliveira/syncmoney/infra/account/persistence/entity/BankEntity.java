package dev.thiagooliveira.syncmoney.infra.account.persistence.entity;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Currency;
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

  public static BankEntity from(CreateBankInput input) {
    BankEntity entity = new BankEntity();
    entity.id = UUID.randomUUID();
    entity.name = input.name();
    entity.organizationId = input.organizationId();
    entity.currency = input.currency();
    return entity;
  }

  public static Bank mapToBank(BankEntity bankEntity) {
    return new Bank(
        bankEntity.getId(),
        bankEntity.getOrganizationId(),
        bankEntity.getName(),
        bankEntity.getCurrency());
  }

  public Bank toBank() {
    return new Bank(id, organizationId, name, currency);
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
