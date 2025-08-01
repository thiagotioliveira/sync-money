package dev.thiagooliveira.syncmoney.infra;

import static dev.thiagooliveira.syncmoney.util.TestUtil.USER_EMAIL;
import static dev.thiagooliveira.syncmoney.util.TestUtil.USER_NAME;

import dev.thiagooliveira.syncmoney.core.user.application.dto.RegisterUserInput;
import dev.thiagooliveira.syncmoney.core.user.application.service.AuthService;
import dev.thiagooliveira.syncmoney.core.user.domain.model.User;
import dev.thiagooliveira.syncmoney.infra.security.service.JwtService;
import dev.thiagooliveira.syncmoney.infra.security.service.UserAuthenticated;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext {

  private String token;

  @Autowired private AuthService authService;
  @Autowired private JwtService jwtService;

  @PostConstruct
  public void init() {
    var user =
        this.authService.register(new RegisterUserInput(USER_EMAIL, USER_NAME, "<PASSWORD>"));

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                new UserAuthenticated(user), null, List.of(new SimpleGrantedAuthority("admin"))));
    token =
        jwtService.generateToken(
            User.restore(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getOrganizationId()));
  }

  public String getToken() {
    return token;
  }
}
