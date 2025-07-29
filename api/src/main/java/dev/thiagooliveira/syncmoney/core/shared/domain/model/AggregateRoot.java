package dev.thiagooliveira.syncmoney.core.shared.domain.model;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {

  private final List<Event> events = new ArrayList<>();

  public void registerEvent(Event event) {
    events.add(event);
  }

  public List<Event> getEvents() {
    return Collections.unmodifiableList(events);
  }
}
