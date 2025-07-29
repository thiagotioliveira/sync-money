package dev.thiagooliveira.syncmoney.infra.account.event.adapter;

import dev.thiagooliveira.syncmoney.core.account.domain.port.income.TransactionEventListener;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.TransactionPaidEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionEventListenerAdapter {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final TransactionEventListener transactionEventListener;

  public TransactionEventListenerAdapter(TransactionEventListener transactionEventListener) {
    this.transactionEventListener = transactionEventListener;
  }

  @EventListener
  @Transactional
  public void on(TransactionPaidEvent event) {
    logger.info("TransactionPaidEvent: {}", event);
    this.transactionEventListener.listen(event);
  }
}
