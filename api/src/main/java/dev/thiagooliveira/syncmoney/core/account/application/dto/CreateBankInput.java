package dev.thiagooliveira.syncmoney.core.account.application.dto;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import java.util.UUID;

public record CreateBankInput(UUID organizationId, String name, Currency currency) {}
