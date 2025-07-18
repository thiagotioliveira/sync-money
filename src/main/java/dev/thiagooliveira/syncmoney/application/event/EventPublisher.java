package dev.thiagooliveira.syncmoney.application.event;

import dev.thiagooliveira.syncmoney.application.event.dto.Event;

public interface EventPublisher {
  void publish(Event event);
}
