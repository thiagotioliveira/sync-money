package dev.thiagooliveira.syncmoney.core.shared.domain.model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DomainEventPublisher {

  private static final ThreadLocal<List<DomainEvent>> events = new ThreadLocal<>();

  public static void init() {
    events.set(new ArrayList<>());
  }

  public static boolean isInitialized() {
    return events.get() != null;
  }

  public static void addEvent(DomainEvent event) {
    events.get().add(event);
  }

  public static void publish(Consumer<? super DomainEvent> action) {
    var e = new ArrayList<>(events.get());
    init();
    e.forEach(action);
  }

  public static void clear() {
    events.remove();
  }
}
