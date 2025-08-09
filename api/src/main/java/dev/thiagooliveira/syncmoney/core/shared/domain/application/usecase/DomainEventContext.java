package dev.thiagooliveira.syncmoney.core.shared.domain.application.usecase;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.DomainEventPublisher;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainEventContext {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EventPublisher eventPublisher;

  public DomainEventContext(EventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public <T> T execute(Supplier<T> useCaseLogic) {
    boolean alreadyInitialized = DomainEventPublisher.isInitialized();
    if (!alreadyInitialized) {
      DomainEventPublisher.init();
      logger.debug(
          "Domain Event Context Initialized - threadId={}", Thread.currentThread().threadId());
    }
    try {
      T t = useCaseLogic.get();
      DomainEventPublisher.publish(this.eventPublisher::publish);
      return t;
    } finally {
      if (!alreadyInitialized) {
        DomainEventPublisher.clear();
        logger.debug(
            "Domain Event Context Cleaner - threadId={}", Thread.currentThread().threadId());
      }
    }
  }
}
