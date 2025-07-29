package dev.thiagooliveira.syncmoney.core.transaction.application.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public record CreateAccountSummaryInput(
    UUID accountId,
    YearMonth yearMonth,
    BigDecimal lastClosingBalance,
    List<TransactionEnriched> transactionsMonth) {}
