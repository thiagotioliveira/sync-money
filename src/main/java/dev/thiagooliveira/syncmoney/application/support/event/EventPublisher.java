package dev.thiagooliveira.syncmoney.application.support.event;

import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;

public interface EventPublisher {
  void publish(Event event);
}
