package dev.thiagooliveira.syncmoney.infra.user.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createUserInput;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.application.user.domain.port.UserPort;
import dev.thiagooliveira.syncmoney.infra.IntegrationTest;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class UserServiceRollbackIT extends IntegrationTest {

  @Autowired private UserService userService;
  @Autowired private OrganizationRepository organizationRepository;

  @MockitoBean private UserPort userPort;

  @Test
  @DisplayName("should not save organization if user is not saved")
  void shouldNotSaveOrganizationIfUserIsNotSave() {
    CreateUserInput input = createUserInput();
    when(userPort.existByEmail(input.email())).thenReturn(false);
    when(userPort.save(eq(input), any(Organization.class)))
        .thenThrow(new RuntimeException("some database error"));

    var exception = assertThrows(RuntimeException.class, () -> userService.create(input));
    assertEquals("some database error", exception.getMessage());

    var orgCount = organizationRepository.count();
    assertEquals(0, orgCount);
  }
}
