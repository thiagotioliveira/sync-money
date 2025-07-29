package dev.thiagooliveira.syncmoney.infra.transaction.api.mapper;

import dev.thiagooliveira.syncmoney.core.transaction.domain.model.AccountSummary;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.GetAccountSummaryResponseBody;
import java.time.LocalDate;
import java.time.YearMonth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountSummaryMapper {

  GetAccountSummaryResponseBody mapToGetAccountSummaryResponseBody(AccountSummary accountSummary);

  default LocalDate mapToLocalDate(YearMonth yearMonth) {
    return yearMonth.atDay(1);
  }
}
