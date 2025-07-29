package dev.thiagooliveira.syncmoney.core.account.application.usecase;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.account.domain.port.outcome.BankRepository;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;

public class CreateBank {

  private final EventPublisher eventPublisher;
  private final BankRepository bankRepository;

  public CreateBank(EventPublisher eventPublisher, BankRepository bankRepository) {
    this.eventPublisher = eventPublisher;
    this.bankRepository = bankRepository;
  }

  public Bank execute(CreateBankInput input) {
    if (this.bankRepository.existsByName(input.organizationId(), input.name())) {
      throw BusinessLogicException.badRequest("bank already exists");
    }
    Bank bank = bankRepository.create(input);
    bank.getEvents().forEach(this.eventPublisher::publish);
    return bank;
  }
}
