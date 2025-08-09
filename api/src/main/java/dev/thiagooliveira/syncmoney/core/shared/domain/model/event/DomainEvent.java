package dev.thiagooliveira.syncmoney.core.shared.domain.model.event;

import java.time.OffsetDateTime;

public interface DomainEvent {

  OffsetDateTime getDateTime();
}
