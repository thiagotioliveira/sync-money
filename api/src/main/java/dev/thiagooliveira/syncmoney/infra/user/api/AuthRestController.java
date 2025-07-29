package dev.thiagooliveira.syncmoney.infra.user.api;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import dev.thiagooliveira.syncmoney.core.user.application.service.UserService;
import dev.thiagooliveira.syncmoney.infra.auth.api.AuthApi;
import dev.thiagooliveira.syncmoney.infra.auth.api.dto.PostLoginRequestBody;
import dev.thiagooliveira.syncmoney.infra.auth.api.dto.PostLoginResponseBody;
import dev.thiagooliveira.syncmoney.infra.auth.api.dto.PostRegisterRequestBody;
import dev.thiagooliveira.syncmoney.infra.auth.api.dto.PostRegisterResponseBody;
import dev.thiagooliveira.syncmoney.infra.security.service.JwtService;
import dev.thiagooliveira.syncmoney.infra.user.api.mapper.AuthMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController implements AuthApi {

  private final AuthMapper authMapper;
  private final UserService userService;
  private final JwtService jwtService;
  private final CredentialEncoder credentialEncoder;

  public AuthRestController(
      AuthMapper authMapper,
      UserService userService,
      JwtService jwtService,
      CredentialEncoder credentialEncoder) {
    this.authMapper = authMapper;
    this.userService = userService;
    this.jwtService = jwtService;
    this.credentialEncoder = credentialEncoder;
  }

  @Override
  public ResponseEntity<PostLoginResponseBody> login(PostLoginRequestBody postLoginRequestBody) {
    var user =
        this.userService
            .getByEmail(postLoginRequestBody.getEmail())
            .orElseThrow(() -> BusinessLogicException.notFound("user not found"));
    if (!this.credentialEncoder.matches(postLoginRequestBody.getPassword(), user.getPassword())) {
      throw BusinessLogicException.badRequest("invalid credentials");
    }

    return ResponseEntity.ok(new PostLoginResponseBody().token(jwtService.generateToken(user)));
  }

  @Override
  public ResponseEntity<PostRegisterResponseBody> register(
      PostRegisterRequestBody postRegisterRequestBody) {
    return ResponseEntity.created(null)
        .body(
            this.authMapper.mapToPostRegisterResponseBody(
                this.userService.create(
                    this.authMapper
                        .mapToCreateUserInput(postRegisterRequestBody)
                        .withCredentialEncoder(credentialEncoder))));
  }
}
