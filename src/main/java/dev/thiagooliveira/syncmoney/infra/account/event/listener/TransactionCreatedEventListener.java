package dev.thiagooliveira.syncmoney.infra.account.event.listener;

import dev.thiagooliveira.syncmoney.application.account.domain.dto.UpdateAccountBalanceInput;
import dev.thiagooliveira.syncmoney.application.transaction.domain.dto.event.TransactionCreatedEvent;
import dev.thiagooliveira.syncmoney.infra.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedEventListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AccountService accountService;

  public TransactionCreatedEventListener(AccountService accountService) {
    this.accountService = accountService;
  }

  @EventListener
  @Async("taskExecutor")
  public void on(TransactionCreatedEvent event) {
    logger.info("TransactionCreatedEvent: {}", event);
    this.accountService.updateAccountBalance(new UpdateAccountBalanceInput(event));
  }
}
