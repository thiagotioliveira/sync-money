package dev.thiagooliveira.syncmoney.infra.security.service;

import dev.thiagooliveira.syncmoney.core.shared.domain.model.CredentialEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialEncoderProxy implements CredentialEncoder {

  private final PasswordEncoder passwordEncoder;

  public CredentialEncoderProxy(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(String credential) {
    return this.passwordEncoder.encode(credential);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return this.passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
