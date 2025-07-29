package dev.thiagooliveira.syncmoney.infra;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostAccountRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostAccountResponseBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankRequestBody;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankResponseBody;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class ITUtil {

  public static PostBankResponseBody createBank(
      MockMvc mockMvc, ObjectMapper objectMapper, SecurityContext securityContext)
      throws Exception {
    var content =
        mockMvc
            .perform(
                post("/api/banks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            new PostBankRequestBody()
                                .name(BANK_NAME)
                                .currency(BANK_CURRENCY.name())))
                    .header("Authorization", "Bearer " + securityContext.getToken()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    return objectMapper.readValue(content, PostBankResponseBody.class);
  }

  public static PostAccountResponseBody createAccount(
      MockMvc mockMvc, ObjectMapper objectMapper, SecurityContext securityContext, UUID bankId)
      throws Exception {
    var content =
        mockMvc
            .perform(
                post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        objectMapper.writeValueAsString(
                            new PostAccountRequestBody()
                                .name(ACCOUNT_NAME)
                                .bankId(bankId)
                                .initialBalance(BigDecimal.TEN)))
                    .header("Authorization", "Bearer " + securityContext.getToken()))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

    return objectMapper.readValue(content, PostAccountResponseBody.class);
  }
}
