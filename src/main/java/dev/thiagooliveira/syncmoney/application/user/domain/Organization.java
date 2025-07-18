package dev.thiagooliveira.syncmoney.application.user.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Organization(UUID id, OffsetDateTime createdAt, String emailOwner) {
  public static Organization create(String emailOwner) {
    return new Organization(UUID.randomUUID(), OffsetDateTime.now(), emailOwner);
  }
}
