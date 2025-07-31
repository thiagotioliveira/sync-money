package dev.thiagooliveira.syncmoney.infra.user.api;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetInvitationsResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.GetUsersResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostInviteUserRequestBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostInviteUserResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.mapper.UserMapper;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController implements UsersApi {

  private final UserMapper userMapper;
  private final UserService userService;

  public UserRestController(UserMapper userMapper, UserService userService) {
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
                .getById(principal.getOrganizationId(), principal.getId())
                .orElseThrow(() -> BusinessLogicException.notFound("user not found"))));
  }

  @Override
  public ResponseEntity<PostInviteUserResponseBody> invite(
      PostInviteUserRequestBody postInviteUserRequestBody) {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.created(null)
        .body(
            this.userMapper.mapToPostInviteUserResponseBody(
                this.userService.invite(
                    this.userMapper.mapToInvitationInput(
                        principal.getId(),
                        principal.getOrganizationId(),
                        postInviteUserRequestBody))));
  }

  @Override
  public ResponseEntity<List<GetInvitationsResponseBody>> listInvitations() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.userService.getAllInvitations(principal.getOrganizationId()).stream()
            .map(this.userMapper::mapToGetInvitationsResponseBody)
            .toList());
  }

  @Override
  public ResponseEntity<List<GetUsersResponseBody>> listUsers() {
    UserAuthenticated principal =
        (UserAuthenticated) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(
        this.userService.getAll(principal.getOrganizationId()).stream()
            .map(this.userMapper::mapToGetUsersResponseBody)
            .toList());
  }
}
