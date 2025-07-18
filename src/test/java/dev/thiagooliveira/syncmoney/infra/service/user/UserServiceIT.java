package dev.thiagooliveira.syncmoney.infra.service.user;

import static dev.thiagooliveira.syncmoney.util.TestUtil.createUserInput;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.thiagooliveira.syncmoney.TestcontainersConfiguration;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.syncmoney.infra.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
  void create() {
    var user = this.userService.create(createUserInput());
    assertNotNull(user);
    var userSaved = this.userRepository.findById(user.getId()).orElseThrow();
    assertNotNull(userSaved);
    var organization =
        this.organizationRepository.findById(userSaved.getOrganizationId()).orElseThrow();
    assertNotNull(organization);
  }
}
