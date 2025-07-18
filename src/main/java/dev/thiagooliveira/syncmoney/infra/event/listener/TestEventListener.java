package dev.thiagooliveira.syncmoney.infra.event.listener;

import dev.thiagooliveira.syncmoney.application.user.event.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @EventListener
  @Async("taskExecutor")
  public void on(UserCreatedEvent event) {
    logger.info("UserCreatedEvent: {}", event);
  }
}
