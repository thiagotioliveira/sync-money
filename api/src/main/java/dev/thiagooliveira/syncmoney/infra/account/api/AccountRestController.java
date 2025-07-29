package dev.thiagooliveira.syncmoney.infra.account.api;

import dev.thiagooliveira.syncmoney.core.account.application.service.AccountService;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.GetAccountResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.GetAccountsResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostAccountRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostAccountResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.mapper.AccountMapper;
import dev.thiagooliveira.syncmoney.infra.account.service.AccountServiceProxy;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController implements AccountsApi {

  private final AccountMapper accountMapper;
  private final AccountService accountService;

  public AccountRestController(AccountMapper accountMapper, AccountServiceProxy accountService) {
    this.accountMapper = accountMapper;
    this.accountService = accountService;
  }

  @Override
  public ResponseEntity<PostAccountResponseBody> createAccount(
      PostAccountRequestBody postAccountRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.created(null)
        .body(
            this.accountMapper.mapToPostAccountResponseBody(
                this.accountService.createAccount(
                    this.accountMapper.mapToCreateAccountInput(
                        principal.organizationId(), principal.id(), postAccountRequestBody))));
  }

  @Override
  public ResponseEntity<GetAccountResponseBody> getById(UUID id) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.accountMapper.mapToGetAccountResponseBody(
            this.accountService
                .getById(principal.organizationId(), id)
                .orElseThrow(() -> BusinessLogicException.notFound("account not found"))));
  }

  @Override
  public ResponseEntity<List<GetAccountsResponseBody>> listAccounts() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.accountService.getAll(principal.organizationId()).stream()
            .map(accountMapper::mapToGetAccountsResponseBody)
            .toList());
  }
}
