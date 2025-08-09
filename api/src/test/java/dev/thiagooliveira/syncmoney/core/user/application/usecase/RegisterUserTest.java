package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Invitation;
import dev.thiagooliveira.syncmoney.core.user.domain.model.InvitationStatus;
import dev.thiagooliveira.syncmoney.core.user.domain.model.Organization;
import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.InvitationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.OrganizationRepository;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterUserTest {
  @Mock private OrganizationRepository organizationRepository;
  @Mock private UserRepository userRepository;
  @Mock private InvitationRepository invitationRepository;
  @InjectMocks private RegisterUser registerUser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateNewUser() {
    var input = new RegisterUserInput("john.due@app.test", "John Due", "some-pass");
    when(this.userRepository.existByEmail(eq(input.email()))).thenReturn(false);
    when(this.invitationRepository.getByEmail(eq(input.email()))).thenReturn(Optional.empty());
    when(this.organizationRepository.save(any(Organization.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(this.userRepository.save(any(UserWithPassword.class)))
        .thenAnswer(invocation -> ((UserWithPassword) invocation.getArgument(0)).toUser());

    var user = this.registerUser.execute(input);
    assertNotNull(user);
    assertEquals(input.email(), user.getEmail());
    assertEquals(input.name(), user.getName());
    verify(this.userRepository, times(1)).save(any(UserWithPassword.class));
    verify(this.invitationRepository, times(1)).getByEmail(eq(input.email()));
    verify(this.organizationRepository, times(1)).save(any(Organization.class));
  }

  @Test
  void shouldThrowExceptionWhenEmailAlreadyExists() {
    var input = new RegisterUserInput("existing.email@app.test", "Existing User", "some-pass");
    when(this.userRepository.existByEmail(eq(input.email()))).thenReturn(true);

    var exception =
        assertThrows(BusinessLogicException.class, () -> this.registerUser.execute(input));
    assertEquals("email already exists", exception.getMessage());

    verify(this.userRepository, times(1)).existByEmail(eq(input.email()));
    verifyNoInteractions(this.organizationRepository);
    verifyNoInteractions(this.invitationRepository);
  }

  @Test
  void shouldCreateUserWithExistingInvitation() {
    var input = new RegisterUserInput("invited.user@app.test", "Invited User", "some-pass");
    var invitation = mock(Invitation.class);
    var organizationId = UUID.randomUUID();
    when(invitation.getOrganizationId()).thenReturn(organizationId);
    when(this.userRepository.existByEmail(eq(input.email()))).thenReturn(false);
    when(this.invitationRepository.getByEmail(eq(input.email())))
        .thenReturn(Optional.of(invitation));
    when(this.organizationRepository.findById(eq(organizationId)))
        .thenReturn(Optional.of(mock(Organization.class)));
    when(this.userRepository.save(any(UserWithPassword.class)))
        .thenAnswer(invocation -> ((UserWithPassword) invocation.getArgument(0)).toUser());

    var user = this.registerUser.execute(input);
    assertNotNull(user);
    assertEquals(input.email(), user.getEmail());
    assertEquals(input.name(), user.getName());

    verify(this.invitationRepository, times(1)).save(invitation.accepted());
    verify(this.organizationRepository, times(1)).findById(eq(organizationId));
    verify(this.userRepository, times(1)).save(any(UserWithPassword.class));
  }

  @Test
  void shouldThrowExceptionForInvalidInvitation() {
    var input =
        new RegisterUserInput(
            "invalid.invitation@app.test", "Invalid Invitation User", "some-pass");
    var invitation = mock(Invitation.class);
    var organizationId = UUID.randomUUID();
    when(invitation.getOrganizationId()).thenReturn(organizationId);
    when(invitation.getStatus()).thenReturn(InvitationStatus.ACCEPTED);
    when(this.userRepository.existByEmail(eq(input.email()))).thenReturn(false);
    when(this.invitationRepository.getByEmail(eq(input.email())))
        .thenReturn(Optional.of(invitation));
    when(this.organizationRepository.findById(eq(organizationId))).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> this.registerUser.execute(input));

    verify(this.organizationRepository, times(1)).findById(eq(organizationId));
  }
}
