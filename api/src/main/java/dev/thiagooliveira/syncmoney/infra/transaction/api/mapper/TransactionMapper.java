package dev.thiagooliveira.syncmoney.infra.transaction.api.mapper;

import dev.thiagooliveira.syncmoney.core.transaction.application.dto.*;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.PayableReceivable;
import dev.thiagooliveira.syncmoney.core.transaction.domain.model.Transaction;
import dev.thiagooliveira.syncmoney.infra.transactions.api.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  PostTransactionsResponseBody mapToPostTransactionsResponseBody(TransactionEnriched transaction);

  CreateTransactionInput mapToCreateTransactionInput(
      UUID organizationId,
      UUID userId,
      UUID accountId,
      PostDepositRequestBody postDepositRequestBody);

  CreateTransactionInput mapToCreateTransactionInput(
      UUID organizationId,
      UUID userId,
      UUID accountId,
      PostWithdrawRequestBody postWithdrawRequestBody);

  CreatePayableReceivableInput mapToCreatePayableReceivableInput(
      UUID organizationId, UUID accountId, PostPayableRequestBody postPayableRequestBody);

  CreatePayableReceivableInput mapToCreatePayableReceivableInput(
      UUID organizationId, UUID accountId, PostReceivableRequestBody postReceivableRequestBody);

  UpdateTransactionInput mapToUpdateTransactionInput(
      PatchTransactionRequestBody patchTransactionRequestBody);

  PayTransactionInput mapToPayTransactionInput(
      UUID organizationId,
      UUID userId,
      UUID accountId,
      UUID transactionId,
      PostPayTransactionRequestBody postPayTransactionRequestBody);

  PostPayTransactionResponseBody mapToPostPayTransactionResponseBody(Transaction transaction);

  PostDepositResponseBody mapToPostDepositResponseBody(Transaction transaction);

  PostWithdrawResponseBody mapToPostWithdrawResponseBody(Transaction transaction);

  PostPayableResponseBody mapToPostPayableResponseBody(PayableReceivable payableReceivable);

  PostReceivableResponseBody mapToPostReceivableResponseBody(PayableReceivable payableReceivable);

  PatchTransactionResponseBody mapToPatchTransactionResponseBody(Transaction installment);

  default UUID mapUUID(Optional<UUID> value) {
    return value.orElse(null);
  }

  default Optional<Integer> map(JsonNullable<Integer> value) {
    return value.isPresent() ? Optional.of(value.get()) : Optional.empty();
  }

  default Optional<LocalDate> mapLocalDate(LocalDate value) {
    return Optional.ofNullable(value);
  }

  default Optional<BigDecimal> mapBigDecimal(BigDecimal value) {
    return Optional.ofNullable(value);
  }

  default Optional<Integer> mapInteger(Integer value) {
    return Optional.ofNullable(value);
  }
}
