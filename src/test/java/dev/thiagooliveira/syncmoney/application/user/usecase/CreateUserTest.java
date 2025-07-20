package dev.thiagooliveira.syncmoney.application.user.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.application.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.application.support.event.EventPublisher;
import dev.thiagooliveira.syncmoney.application.support.event.domain.dto.Event;
import dev.thiagooliveira.syncmoney.application.user.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.syncmoney.application.user.domain.port.OrganizationPort;
import dev.thiagooliveira.syncmoney.application.user.domain.port.UserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateUserTest {

  private EventPublisher eventPublisher;
  private OrganizationPort organizationPort;
  private UserPort userPort;

  private CreateUser createUser;

  @BeforeEach
  void setUp() {
    this.eventPublisher = mock(EventPublisher.class);
    this.organizationPort = mock(OrganizationPort.class);
    this.userPort = mock(UserPort.class);
    this.createUser = new CreateUser(eventPublisher, organizationPort, userPort);
  }

  @Test
  @DisplayName("should to create a new user")
  void shouldCreateNewUser() {
    // Given
    var input = createUserInput();
    // When
    when(this.userPort.existByEmail(eq(input.email()))).thenReturn(false);

    var organization = createOrganization();
    when(this.organizationPort.save(any(CreateOrganizationInput.class))).thenReturn(organization);

    when(this.userPort.save(eq(input), eq(organization))).thenReturn(createUser(organization));
    // Then
    var user = this.createUser.execute(input);
    verify(userPort, times(1)).save(eq(input), eq(organization));
    verify(eventPublisher, times(1)).publish(any(Event.class));
    assertNotNull(user);
    assertNotNull(user.getId());
    assertNotNull(user.getCreatedAt());
    assertNotNull(user.getOrganizationId());
    assertEquals(input.name(), user.getName());
    assertEquals(input.email(), user.getEmail());
  }

  @Test
  @DisplayName("should get error when try to create a user with duplicate email")
  void shouldCreateNewUserWithDuplicateEmail() {
    // Given
    var input = createUserInput();
    // When
    when(this.userPort.existByEmail(eq(input.email()))).thenReturn(true);
    // Then
    assertThrows(BusinessLogicException.class, () -> this.createUser.execute(input));
  }
}
