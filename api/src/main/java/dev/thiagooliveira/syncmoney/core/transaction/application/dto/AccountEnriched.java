package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import java.util.UUID;

public record AccountEnriched(UUID id, String name, Currency currency) {}
