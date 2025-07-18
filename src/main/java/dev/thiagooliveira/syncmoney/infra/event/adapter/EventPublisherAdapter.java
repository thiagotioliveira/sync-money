package dev.thiagooliveira.syncmoney.infra.event.adapter;

import dev.thiagooliveira.syncmoney.application.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.event.dto.Event;
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
