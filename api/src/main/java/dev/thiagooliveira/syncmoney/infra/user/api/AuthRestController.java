package dev.thiagooliveira.syncmoney.infra.user.api;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import dev.thiagooliveira.syncmoney.core.user.application.service.AuthService;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostLoginRequestBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostLoginResponseBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostRegisterRequestBody;
import dev.thiagooliveira.syncmoney.infra.user.api.dto.PostRegisterResponseBody;
import dev.thiagooliveira.syncmoney.infra.security.service.JwtService;
import dev.thiagooliveira.syncmoney.infra.user.api.mapper.AuthMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController implements AuthApi {

  private final AuthMapper authMapper;
  private final AuthService authService;
  private final JwtService jwtService;
  private final CredentialEncoder credentialEncoder;

  public AuthRestController(
      AuthMapper authMapper,
      AuthService authService,
      JwtService jwtService,
      CredentialEncoder credentialEncoder) {
    this.authMapper = authMapper;
    this.authService = authService;
    this.jwtService = jwtService;
    this.credentialEncoder = credentialEncoder;
  }

  @Override
  public ResponseEntity<PostLoginResponseBody> login(PostLoginRequestBody postLoginRequestBody) {
    var user =
        this.authService.login(postLoginRequestBody.getEmail(), postLoginRequestBody.getPassword());
    return ResponseEntity.ok(new PostLoginResponseBody().token(jwtService.generateToken(user)));
  }

  @Override
  public ResponseEntity<PostRegisterResponseBody> register(
      PostRegisterRequestBody postRegisterRequestBody) {
    return ResponseEntity.created(null)
        .body(
            this.authMapper.mapToPostRegisterResponseBody(
                this.authService.register(
                    this.authMapper
                        .mapToRegisterUserInput(postRegisterRequestBody)
                        .withCredentialEncoder(credentialEncoder))));
  }
}
