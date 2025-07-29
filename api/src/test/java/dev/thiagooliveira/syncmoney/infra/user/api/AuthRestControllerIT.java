package dev.thiagooliveira.syncmoney.infra.user.api;

import static dev.thiagooliveira.syncmoney.util.TestUtil.USER_EMAIL;
import static dev.thiagooliveira.syncmoney.util.TestUtil.USER_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.auth.api.dto.PostRegisterRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
class AuthRestControllerIT extends IntegrationTest {

  @Autowired private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    this.objectMapper = new ObjectMapper();
  }

  @Test
  void login() throws Exception {
    register("Marcus Stelvio", "marcus.s@test.com", "<PASSWORD>");
    mockMvc
        .perform(
            post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new PostRegisterRequestBody()
                            .email("marcus.s@test.com")
                            .password("<PASSWORD>"))))
        .andExpect(status().isOk());
  }

  @Test
  void register() throws Exception {
    register(USER_NAME, USER_EMAIL, "<PASSWORD>");
  }

  private void register(String name, String email, String password) throws Exception {
    mockMvc
        .perform(
            post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new PostRegisterRequestBody().name(name).email(email).password(password))))
        .andExpect(status().isCreated());
  }
}
