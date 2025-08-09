package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetBank;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventScoped;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import java.util.List;
import java.util.UUID;

public class BankServiceImpl implements BankService {

  private final EventPublisher eventPublisher;
  private final CreateBank createBank;
  private final GetBank getBank;

  public BankServiceImpl(EventPublisher eventPublisher, CreateBank createBank, GetBank getBank) {
    this.eventPublisher = eventPublisher;
    this.createBank = createBank;
    this.getBank = getBank;
  }

  @DomainEventScoped
  @Override
  public Bank createBank(CreateBankInput input) {
    Bank bank = this.createBank.execute(input);
    DomainEventPublisher.publish(this.eventPublisher::publish);
    return bank;
  }

  @Override
  public List<Bank> getAll(UUID organizationId) {
    return this.getBank.getAll(organizationId);
  }
}
