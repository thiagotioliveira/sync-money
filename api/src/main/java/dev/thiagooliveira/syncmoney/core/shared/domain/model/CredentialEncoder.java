package dev.thiagooliveira.syncmoney.core.shared.domain.model;

public interface CredentialEncoder {
  String encode(String credential);

  boolean matches(CharSequence rawPassword, String encodedPassword);
}
