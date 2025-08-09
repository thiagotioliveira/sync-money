package dev.thiagooliveira.syncmoney.core.account.application.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.CreateBank;
import dev.thiagooliveira.syncmoney.core.account.application.usecase.GetBank;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase.DomainEventContext;
import java.util.List;
import java.util.UUID;

public class BankServiceImpl implements BankService {

  private final DomainEventContext domainEventContext;
  private final CreateBank createBank;
  private final GetBank getBank;

  public BankServiceImpl(
      DomainEventContext domainEventContext, CreateBank createBank, GetBank getBank) {
    this.domainEventContext = domainEventContext;
    this.createBank = createBank;
    this.getBank = getBank;
  }

  @Override
  public Bank createBank(CreateBankInput input) {
    return this.domainEventContext.execute(() -> this.createBank.execute(input));
  }

  @Override
  public List<Bank> getAll(UUID organizationId) {
    return this.getBank.getAll(organizationId);
  }
}
