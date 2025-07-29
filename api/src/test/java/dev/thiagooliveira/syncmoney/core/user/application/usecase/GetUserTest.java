package dev.thiagooliveira.syncmoney.core.user.application.usecase;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createOrganization;
import static dev.thiagooliveira.syncmoney.util.TestUtil.getUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import dev.thiagooliveira.syncmoney.util.TestUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetUserTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private GetUser getUser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("should get all users")
  void shouldGetAllUsers() {
    var organization = createOrganization();
    when(this.userRepository.getAll(eq(organization.getId())))
        .thenReturn(List.of(getUser(organization)));
    var users = this.getUser.all(organization.getId());
    assertNotNull(users);
    assertEquals(1, users.size());
  }

  @Test
  @DisplayName("should get user by id")
  void shouldGetUserById() {
    var organization = createOrganization();
    var user = getUser(organization);
    var userId = user.getId();

    when(this.userRepository.getById(eq(organization.getId()), eq(userId)))
        .thenReturn(Optional.of(user));

    var result = this.getUser.byId(organization.getId(), userId);

    assertTrue(result.isPresent());
    assertEquals(userId, result.get().getId());
    assertEquals(organization.getId(), result.get().getOrganizationId());
  }

  @Test
  @DisplayName("should get user by email")
  void getByEmail() {
    var organization = createOrganization();
    var userWithPassword = TestUtil.getUserWithPassword(organization);
    var email = userWithPassword.getEmail();

    when(this.userRepository.getByEmail(eq(email))).thenReturn(Optional.of(userWithPassword));

    var result = this.getUser.getByEmail(email);

    assertTrue(result.isPresent());
    assertEquals(email, result.get().getEmail());
    assertNotNull(result.get().getPassword());
  }
}
