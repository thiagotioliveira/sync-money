package dev.thiagooliveira.syncmoney.infra.shared.adapter;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

public class EventPublisherAdapter implements EventPublisher {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ApplicationEventPublisher applicationEventPublisher;

  public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publish(DomainEvent event) {
    logger.debug("Publishing event: {}", event);
    this.applicationEventPublisher.publishEvent(event);
  }
}
