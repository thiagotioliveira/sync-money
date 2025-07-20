package dev.thiagooliveira.syncmoney.application.transaction.domain.dto.projection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record TransactionEnriched(
    UUID id,
    UUID accountId,
    OffsetDateTime dateTime,
    String description,
    Category category,
    BigDecimal amount,
    String currency) {
  public TransactionEnriched(
      String id,
      String accountId,
      Timestamp dateTime,
      String description,
      String categoryId,
      String categoryName,
      String categoryType,
      BigDecimal amount,
      String currency) {
    this(
        UUID.fromString(id),
        UUID.fromString(accountId),
        dateTime.toInstant().atZone(ZoneId.of("Europe/Lisbon")).toOffsetDateTime(), // TODO
        description,
        new Category(UUID.fromString(categoryId), categoryName, categoryType),
        amount,
        currency);
  }

  public static class Category {
    private final UUID id;
    private final String name;
    private final String type;

    public Category(UUID id, String name, String type) {
      this.id = id;
      this.name = name;
      this.type = type;
    }

    public UUID getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }
  }
}
