package dev.thiagooliveira.syncmoney.application.account.domain.dto;

import dev.thiagooliveira.syncmoney.application.account.domain.model.Currency;
import java.util.UUID;

public record CreateBankInput(UUID organizationId, String name, Currency currency) {}
