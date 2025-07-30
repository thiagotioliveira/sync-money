package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.event.user.UserRegisteredEvent;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.shared.port.outcome.EventPublisher;
import dev.thiagooliveira.syncmoney.core.user.application.dto.CreateUserInOrganizationInput;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterUserTest {

  @Mock private EventPublisher eventPublisher;
  @Mock private OrganizationRepository organizationRepository;
  @Mock private UserRepository userRepository;

  @InjectMocks private RegisterUser registerUser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("should to create a new user")
  void shouldCreateNewUser() {
    var input = createUserInput();
    when(this.userRepository.existByEmail(input.email())).thenReturn(false);
    var organization = createOrganization();
    when(this.organizationRepository.create(eq(input.toCreateOrganizationInput())))
        .thenReturn(organization);
    when(this.userRepository.register(eq(input), eq(organization)))
        .thenReturn(createUser(organization));

    var user = this.registerUser.execute(input);
    assertNotNull(user);
    verify(this.eventPublisher, times(1)).publish(any(UserRegisteredEvent.class));
  }

  @Test
  @DisplayName("should get a error when try to create user with email already register")
  void shouldCreateNewUserWithDuplicateEmail() {
    var input = createUserInput();
    when(this.userRepository.existByEmail(input.email())).thenReturn(true);
    var ex = assertThrows(BusinessLogicException.class, () -> this.registerUser.execute(input));
    assertEquals("email already exists", ex.getMessage());
    assertEquals(400, ex.errorCode());
    verify(this.eventPublisher, times(0)).publish(any(UserRegisteredEvent.class));
  }

  @Test
  @Disabled
  @DisplayName("should to invite a new user ")
  void shouldInviteNewUser() {
    var organization = createOrganization();
    var input =
        new CreateUserInOrganizationInput(
            "user-invited@test.com", "User Invited", "some-pass", organization.getId());
    when(this.userRepository.existByEmail(input.email())).thenReturn(false);
    when(this.organizationRepository.findById(eq(organization.getId())))
        .thenReturn(Optional.of(organization));
    when(this.userRepository.register(any(RegisterUserInput.class), eq(organization)))
        .thenReturn(createUser(organization));
    var user = this.registerUser.execute(input);
    assertNotNull(user);
    verify(this.eventPublisher, times(1)).publish(any(UserRegisteredEvent.class));
  }
}
