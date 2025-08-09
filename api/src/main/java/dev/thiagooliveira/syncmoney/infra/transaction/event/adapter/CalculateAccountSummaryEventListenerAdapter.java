package dev.thiagooliveira.syncmoney.infra.transaction.event.adapter;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.transaction.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.port.income.CalculateAccountSummaryEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CalculateAccountSummaryEventListenerAdapter {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final CalculateAccountSummaryEventListener calculateAccountSummaryEventListener;

  public CalculateAccountSummaryEventListenerAdapter(
      CalculateAccountSummaryEventListener calculateAccountSummaryEventListener) {
    this.calculateAccountSummaryEventListener = calculateAccountSummaryEventListener;
  }

  @EventListener
  @Transactional
  public void on(TransactionScheduledCreatedEvent event) {
    logger.debug("Handling TransactionScheduledCreatedEvent: {}", event);
    this.calculateAccountSummaryEventListener.on(event);
  }

  @EventListener
  @Transactional
  public void on(TransactionPaidEvent event) {
    logger.debug("Handling TransactionPaidEvent: {}", event);
    this.calculateAccountSummaryEventListener.on(event);
  }

  @EventListener
  @Transactional
  public void on(TransactionUpdatedEvent event) {
    logger.debug("Handling TransactionUpdatedEvent: {}", event);
    this.calculateAccountSummaryEventListener.on(event);
  }

  @EventListener
  @Transactional
  public void on(PayableReceivableCreatedEvent event) {
    logger.debug("Handling PayableReceivableCreatedEvent: {}", event);
    this.calculateAccountSummaryEventListener.on(event);
  }

  @EventListener
  @Transactional
  public void on(PayableReceivableUpdatedEvent event) {
    logger.debug("Handling PayableReceivableUpdatedEvent: {}", event);
    this.calculateAccountSummaryEventListener.on(event);
  }
}
