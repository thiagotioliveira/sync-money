package dev.thiagooliveira.syncmoney.application.account.usecase;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.application.account.domain.dto.event.BankCreatedEvent;
import dev.thiagooliveira.syncmoney.application.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.application.account.domain.port.BankPort;
import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;

public class CreateBank {

  private final EventPublisher eventPublisher;
  private final BankPort bankPort;

  public CreateBank(EventPublisher eventPublisher, BankPort bankPort) {
    this.eventPublisher = eventPublisher;
    this.bankPort = bankPort;
  }

  public Bank execute(CreateBankInput input) {
    if (this.bankPort.existsByName(input.organizationId(), input.name())) {
      throw BusinessLogicException.badRequest("bank already exists");
    }
    Bank bank = bankPort.create(input);
    this.eventPublisher.publish(new BankCreatedEvent(bank));
    return bank;
  }
}
