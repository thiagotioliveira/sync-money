package dev.thiagooliveira.syncmoney.infra.transaction.api;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.transaction.api.mapper.AccountSummaryMapper;
import dev.thiagooliveira.syncmoney.infra.transactions.api.AccountsSummaryApi;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.GetAccountSummaryResponseBody;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountSummaryRestController implements AccountsSummaryApi {

  private final AccountSummaryService accountSummaryService;
  private final AccountSummaryMapper accountSummaryMapper;

  public AccountSummaryRestController(
      AccountSummaryService accountSummaryService, AccountSummaryMapper accountSummaryMapper) {
    this.accountSummaryService = accountSummaryService;
    this.accountSummaryMapper = accountSummaryMapper;
  }

  @Override
  public ResponseEntity<GetAccountSummaryResponseBody> getSummaryByAccountId(
      UUID accountId, LocalDate yearMonth) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.accountSummaryMapper.mapToGetAccountSummaryResponseBody(
            this.accountSummaryService.getSummary(
                principal.getOrganizationId(),
                accountId,
                YearMonth.of(yearMonth.getYear(), yearMonth.getMonth()))));
  }
}
