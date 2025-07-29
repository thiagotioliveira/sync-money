package dev.thiagooliveira.syncmoney.infra.transaction.api;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.transaction.api.mapper.TransactionMapper;
import dev.thiagooliveira.syncmoney.infra.transaction.service.TransactionServiceProxy;
import dev.thiagooliveira.syncmoney.infra.transactions.api.TransactionsApi;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionRestController implements TransactionsApi {

  private final TransactionMapper transactionMapper;
  private final TransactionService transactionService;

  public TransactionRestController(
      TransactionMapper transactionMapper, TransactionServiceProxy transactionService) {
    this.transactionMapper = transactionMapper;
    this.transactionService = transactionService;
  }

  @Override
  public ResponseEntity<PostDepositResponseBody> createDeposit(
      UUID accountId, PostDepositRequestBody postDepositRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostDepositResponseBody(
            this.transactionService.createDeposit(
                this.transactionMapper.mapToCreateTransactionInput(
                    principal.organizationId(),
                    principal.id(),
                    accountId,
                    postDepositRequestBody))));
  }

  @Override
  public ResponseEntity<PostWithdrawResponseBody> createWithdraw(
      UUID accountId, PostWithdrawRequestBody postWithdrawRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostWithdrawResponseBody(
            this.transactionService.createWithdraw(
                this.transactionMapper.mapToCreateTransactionInput(
                    principal.organizationId(),
                    principal.id(),
                    accountId,
                    postWithdrawRequestBody))));
  }

  @Override
  public ResponseEntity<List<GetTransactionsResponseBody>> getTransactionByAccountId(
      UUID accountId, LocalDate yearMonth) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToGetTransactionsResponseBody(
            this.transactionService.getByAccountId(
                principal.organizationId(),
                accountId,
                YearMonth.of(yearMonth.getYear(), yearMonth.getMonth()))));
  }

  @Override
  public ResponseEntity<PostPayableResponseBody> createPayable(
      UUID accountId, PostPayableRequestBody postPayableRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostPayableResponseBody(
            this.transactionService.createPayable(
                this.transactionMapper.mapToCreatePayableReceivableInput(
                    principal.organizationId(), accountId, postPayableRequestBody))));
  }

  @Override
  public ResponseEntity<PostReceivableResponseBody> createReceivable(
      UUID accountId, PostReceivableRequestBody postReceivableRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostReceivableResponseBody(
            this.transactionService.createReceivable(
                this.transactionMapper.mapToCreatePayableReceivableInput(
                    principal.organizationId(), accountId, postReceivableRequestBody))));
  }

  @Override
  public ResponseEntity<PostPayTransactionResponseBody> payScheduledTransaction(
      UUID accountId,
      UUID transactionId,
      PostPayTransactionRequestBody postPayTransactionRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostPayTransactionResponseBody(
            this.transactionService.pay(
                this.transactionMapper.mapToPayTransactionInput(
                    principal.organizationId(),
                    principal.id(),
                    accountId,
                    transactionId,
                    postPayTransactionRequestBody))));
  }

  @Override
  public ResponseEntity<PatchTransactionResponseBody> updateScheduledTransaction(
      UUID accountId, UUID transactionId, PatchTransactionRequestBody patchTransactionRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPatchTransactionResponseBody(
            this.transactionService.update(
                principal.organizationId(),
                accountId,
                transactionId,
                this.transactionMapper.mapToUpdateTransactionInput(patchTransactionRequestBody))));
  }
}
