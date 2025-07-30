package dev.thiagooliveira.syncmoney.infra.account.service;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.application.service.BankService;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
public class BankServiceProxy implements BankService {

  private final BankService bankService;

  public BankServiceProxy(BankService bankService) {
    this.bankService = bankService;
  }

  @Transactional
  @Override
  public Bank createBank(CreateBankInput input) {
    return this.bankService.createBank(input);
  }
}
