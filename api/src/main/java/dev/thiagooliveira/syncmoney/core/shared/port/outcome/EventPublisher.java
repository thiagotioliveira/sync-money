package dev.thiagooliveira.syncmoney.core.shared.port.outcome;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;

public interface EventPublisher {
  void publish(Event event);
}
