package dev.thiagooliveira.syncmoney.core.shared.domain.model.event;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class DomainEventPublisher {

  private static final ThreadLocal<Set<DomainEvent>> events = ThreadLocal.withInitial(HashSet::new);

  public static void init() {
    events.get().clear();
  }

  public static void addEvent(DomainEvent event) {
    events.get().add(event);
  }

  public static void publish(Consumer<? super DomainEvent> action) {
    var e = new HashSet<>(events.get());
    clear();
    e.forEach(action);
  }

  public static void clear() {
    events.remove();
  }
}
