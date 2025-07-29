package dev.thiagooliveira.syncmoney.infra.account.api;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.syncmoney.infra.ITUtil;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.SecurityContext;
import dev.thiagooliveira.syncmoney.infra.account.api.dto.PostBankResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
class BankRestControllerIT extends IntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private SecurityContext securityContext;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  void createBank() throws Exception {
    PostBankResponseBody responseBody =
        ITUtil.createBank(this.mockMvc, this.objectMapper, this.securityContext);
    assertNotNull(responseBody.getId());
  }
}
