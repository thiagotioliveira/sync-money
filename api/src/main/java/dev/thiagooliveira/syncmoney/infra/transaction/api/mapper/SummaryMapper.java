package dev.thiagooliveira.syncmoney.infra.transaction.api.mapper;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.AggregateAccountSummary;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostSummaryResponseBody;
import java.time.LocalDate;
import java.time.YearMonth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummaryMapper {

  default LocalDate mapToLocalDate(YearMonth yearMonth) {
    return yearMonth.atDay(1);
  }

  PostSummaryResponseBody mapToPostSummaryResponseBody(
      AggregateAccountSummary aggregateAccountSummary);
}
