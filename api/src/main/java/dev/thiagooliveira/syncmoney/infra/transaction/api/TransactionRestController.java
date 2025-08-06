package dev.thiagooliveira.syncmoney.infra.transaction.api;

import dev.thiagooliveira.syncmoney.core.transaction.application.service.TransactionService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.transaction.api.mapper.TransactionMapper;
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
      TransactionMapper transactionMapper, TransactionService transactionService) {
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
                    principal.getOrganizationId(),
                    principal.getId(),
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
                    principal.getOrganizationId(),
                    principal.getId(),
                    accountId,
                    postWithdrawRequestBody))));
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
                    principal.getOrganizationId(), accountId, postPayableRequestBody))));
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
                    principal.getOrganizationId(), accountId, postReceivableRequestBody))));
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
                    principal.getOrganizationId(),
                    principal.getId(),
                    accountId,
                    transactionId,
                    postPayTransactionRequestBody))));
  }

  @Override
  public ResponseEntity<List<PostTransactionsResponseBody>> postTransactionsByAccountIds(
      LocalDate yearMonth, PostTransactionsRequestBody postTransactionsRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionService
            .getByAccountIds(
                principal.getOrganizationId(),
                postTransactionsRequestBody.getAccountIds(),
                YearMonth.of(yearMonth.getYear(), yearMonth.getMonth()))
            .stream()
            .map(this.transactionMapper::mapToPostTransactionsResponseBody)
            .toList());
  }

  @Override
  public ResponseEntity<PostTransferResponseBody> postTransfer(
      PostTransferRequestBody postTransferRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPostTransferResponseBody(
            this.transactionService.transfer(
                this.transactionMapper.mapToCreateTransferInput(
                    principal.getOrganizationId(), principal.getId(), postTransferRequestBody))));
  }

  @Override
  public ResponseEntity<PatchTransactionResponseBody> updateScheduledTransaction(
      UUID accountId, UUID transactionId, PatchTransactionRequestBody patchTransactionRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.transactionMapper.mapToPatchTransactionResponseBody(
            this.transactionService.update(
                principal.getOrganizationId(),
                accountId,
                transactionId,
                this.transactionMapper.mapToUpdateTransactionInput(patchTransactionRequestBody))));
  }
}
