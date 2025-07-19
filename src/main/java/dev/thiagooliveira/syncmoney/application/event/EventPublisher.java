package dev.thiagooliveira.syncmoney.application.event;

import dev.thiagooliveira.syncmoney.application.event.domain.dto.Event;

public interface EventPublisher {
  void publish(Event event);
}
