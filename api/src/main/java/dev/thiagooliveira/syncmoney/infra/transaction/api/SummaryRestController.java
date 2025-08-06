package dev.thiagooliveira.syncmoney.infra.transaction.api;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.AccountSummaryService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.transaction.api.mapper.SummaryMapper;
import dev.thiagooliveira.syncmoney.infra.transactions.api.SummaryApi;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostSummaryRequestBody;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.PostSummaryResponseBody;
import java.time.LocalDate;
import java.time.YearMonth;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SummaryRestController implements SummaryApi {

  private final AccountSummaryService accountSummaryService;
  private final SummaryMapper summaryMapper;

  public SummaryRestController(
      AccountSummaryService accountSummaryService, SummaryMapper summaryMapper) {
    this.accountSummaryService = accountSummaryService;
    this.summaryMapper = summaryMapper;
  }

  @Override
  public ResponseEntity<PostSummaryResponseBody> postSummary(
      LocalDate yearMonth, PostSummaryRequestBody postSummaryRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.summaryMapper.mapToPostSummaryResponseBody(
            this.accountSummaryService.getAggregateSummary(
                principal.getOrganizationId(),
                postSummaryRequestBody.getAccountIds(),
                YearMonth.of(yearMonth.getYear(), yearMonth.getMonth()))));
  }
}
