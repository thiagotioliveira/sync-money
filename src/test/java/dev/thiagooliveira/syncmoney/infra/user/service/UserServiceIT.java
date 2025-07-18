package dev.thiagooliveira.syncmoney.infra.user.service;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createUserInput;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.syncmoney.TestcontainersConfiguration;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.syncmoney.infra.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class UserServiceIT {

  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private OrganizationRepository organizationRepository;

  @BeforeEach
  void setUp() {}

  @Test
  @DisplayName("should to create a new user")
  void shouldCreateNewUser() {
    var user = this.userService.create(createUserInput());
    assertNotNull(user);
    var userSaved = this.userRepository.findById(user.getId()).orElseThrow();
    assertNotNull(userSaved);
    var organization =
        this.organizationRepository.findById(userSaved.getOrganizationId()).orElseThrow();
    assertNotNull(organization);
  }
}
