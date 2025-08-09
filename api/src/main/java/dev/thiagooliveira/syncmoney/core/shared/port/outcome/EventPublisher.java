package dev.thiagooliveira.syncmoney.core.shared.port.outcome;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEvent;

public interface EventPublisher {
  void publish(DomainEvent event);
}
