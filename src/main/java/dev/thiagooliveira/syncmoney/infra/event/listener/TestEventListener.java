package dev.thiagooliveira.syncmoney.infra.event.listener;

import dev.thiagooliveira.syncmoney.application.event.dto.Event;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

  @EventListener
  void on(Event event) {
    System.out.println(event);
  }
}
