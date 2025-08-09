package dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import java.util.function.Supplier;

public class DomainEventContext {

  private final EventPublisher eventPublisher;

  public DomainEventContext(EventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public <T> T execute(Supplier<T> useCaseLogic) {
    DomainEventPublisher.init();
    try {
      T t = useCaseLogic.get();
      DomainEventPublisher.publish(this.eventPublisher::publish);
      return t;
    } finally {
      DomainEventPublisher.clear();
    }
  }
}
