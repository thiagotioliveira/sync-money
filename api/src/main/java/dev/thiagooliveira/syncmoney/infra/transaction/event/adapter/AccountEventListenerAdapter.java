package dev.thiagooliveira.syncmoney.infra.transaction.event.adapter;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.account.AccountCreatedEvent;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.income.AccountEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountEventListenerAdapter {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final AccountEventListener accountEventListener;

  public AccountEventListenerAdapter(AccountEventListener accountEventListener) {
    this.accountEventListener = accountEventListener;
  }

  @EventListener
  @Transactional
  public void handle(AccountCreatedEvent event) {
    logger.info("Handling AccountCreatedEvent: {}", event);
    this.accountEventListener.listen(event);
  }
}
