package dev.thiagooliveira.syncmoney.infra.security.service;

import dev.thiagooliveira.syncmoney.core.user.domain.model.UserWithPassword;
import dev.thiagooliveira.syncmoney.core.user.domain.port.outcome.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  public JwtFilter(JwtService jwtService, UserRepository userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    String email = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
      email = jwtService.extractUsername(jwt);
    }

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserWithPassword user = userRepository.getByEmail(email).orElse(null);
      if (user != null
          && jwtService.validateToken(
              jwt,
              new org.springframework.security.core.userdetails.User(
                  user.getEmail(),
                  user.getPassword(),
                  List.of(new SimpleGrantedAuthority("admin"))))) {

        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                new UserAuthenticated(
                    user.getId(), user.getOrganizationId(), user.getName(), user.getEmail()),
                null,
                List.of(new SimpleGrantedAuthority("admin")));

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    chain.doFilter(request, response);
  }
}
