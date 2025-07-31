package dev.thiagooliveira.syncmoney.infra.account.api;

import dev.thiagooliveira.syncmoney.core.account.application.service.BankService;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.GetBanksResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.mapper.BankMapper;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestController implements BanksApi {

  private final BankMapper bankMapper;
  private final BankService bankService;

  public BankRestController(BankMapper bankMapper, BankService bankService) {
    this.bankMapper = bankMapper;
    this.bankService = bankService;
  }

  @Override
  public ResponseEntity<PostBankResponseBody> createBank(PostBankRequestBody postBankRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.created(null)
        .body(
            this.bankMapper.mapToPostBankResponseBody(
                this.bankService.createBank(
                    this.bankMapper.mapToCreateBankInput(
                        principal.getOrganizationId(), postBankRequestBody))));
  }

  @Override
  public ResponseEntity<List<GetBanksResponseBody>> getBanks() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.bankService.getAll(principal.getOrganizationId()).stream()
            .map(this.bankMapper::mapToGetBanksResponseBody)
            .toList());
  }
}
