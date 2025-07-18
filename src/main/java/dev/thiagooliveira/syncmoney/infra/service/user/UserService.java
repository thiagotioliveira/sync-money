package dev.thiagooliveira.syncmoney.infra.service.user;

import dev.thiagooliveira.syncmoney.application.user.domain.User;
import dev.thiagooliveira.syncmoney.application.user.dto.CreateUserInput;
import dev.thiagooliveira.syncmoney.application.user.usecase.CreateUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final CreateUser createUser;

  public UserService(CreateUser createUser) {
    this.createUser = createUser;
  }

  @Transactional
  public User create(CreateUserInput input) {
    return this.createUser.execute(input);
  }
}
