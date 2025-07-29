package dev.thiagooliveira.syncmoney.infra.account.api;

import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.mapper.BankMapper;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestController implements BanksApi {

  private final BankMapper bankMapper;
  private final AccountService accountService;

  public BankRestController(BankMapper bankMapper, AccountService accountService) {
    this.bankMapper = bankMapper;
    this.accountService = accountService;
  }

  @Override
  public ResponseEntity<PostBankResponseBody> createBank(PostBankRequestBody postBankRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.created(null)
        .body(
            this.bankMapper.mapToPostBankResponseBody(
                this.accountService.createBank(
                    this.bankMapper.mapToCreateBankInput(
                        principal.getOrganizationId(), postBankRequestBody))));
  }
}
