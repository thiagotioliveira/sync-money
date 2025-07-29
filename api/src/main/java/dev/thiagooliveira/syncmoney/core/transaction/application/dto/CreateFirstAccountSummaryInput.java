package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import java.time.YearMonth;
import java.util.UUID;

public record CreateFirstAccountSummaryInput(UUID accountId, YearMonth yearMonth) {}
