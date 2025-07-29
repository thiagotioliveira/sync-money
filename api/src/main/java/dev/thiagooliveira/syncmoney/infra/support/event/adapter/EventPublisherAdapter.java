package dev.thiagooliveira.syncmoney.infra.support.event.adapter;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;

public class EventPublisherAdapter implements EventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publish(Event event) {
    this.applicationEventPublisher.publishEvent(event);
  }
}
