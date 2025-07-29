package dev.thiagooliveira.syncmoney.infra.user.api;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetUsersResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.mapper.UserMapper;
import dev.thiagooliveira.syncmoney.infra.user.service.UserServiceProxy;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController implements UsersApi {

  private final UserMapper userMapper;
  private final UserService userService;

  public UserRestController(UserMapper userMapper, UserServiceProxy userService) {
    this.userMapper = userMapper;
    this.userService = userService;
  }

  @Override
  public ResponseEntity<GetUsersResponseBody> getById() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.userMapper.mapToGetUsersResponseBody(
            this.userService
                .getById(principal.organizationId(), principal.id())
                .orElseThrow(() -> BusinessLogicException.notFound("user not found"))));
  }

  @Override
  public ResponseEntity<List<GetUsersResponseBody>> listUsers() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.userService.getAll(principal.organizationId()).stream()
            .map(this.userMapper::mapToGetUsersResponseBody)
            .toList());
  }
}
