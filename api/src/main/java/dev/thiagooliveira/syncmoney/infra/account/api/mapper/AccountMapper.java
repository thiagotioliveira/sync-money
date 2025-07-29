package dev.thiagooliveira.syncmoney.infra.account.api.mapper;

import dev.thiagooliveira.syncmoney.core.account.application.dto.AccountEnriched;
import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateAccountInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Account;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.*;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  GetBanksResponseBody mapToGetBanksResponseBody(Bank bank);

  GetAccountResponseBody mapToGetAccountResponseBody(AccountEnriched account);

  GetAccountsResponseBody mapToGetAccountsResponseBody(Account account);

  PostAccountResponseBody mapToPostAccountResponseBody(Account account);

  CreateAccountInput mapToCreateAccountInput(
      UUID organizationId, UUID userId, PostAccountRequestBody postAccountRequestBody);
}
