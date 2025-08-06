package dev.thiagooliveira.syncmoney.infra.account.api.mapper;

import dev.thiagooliveira.syncmoney.core.account.application.dto.CreateBankInput;
import dev.thiagooliveira.syncmoney.core.account.domain.model.Bank;
import dev.thiagooliveira.syncmoney.core.shared.domain.model.Currency;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.GetBanksResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankResponseBody;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {

  CreateBankInput mapToCreateBankInput(
      UUID organizationId, PostBankRequestBody postBankRequestBody);

  PostBankResponseBody mapToPostBankResponseBody(Bank bank);

  GetBanksResponseBody mapToGetBanksResponseBody(Bank bank);

  default Currency mapToCurrency(String currency) {
    return Currency.valueOf(currency);
  }
}
