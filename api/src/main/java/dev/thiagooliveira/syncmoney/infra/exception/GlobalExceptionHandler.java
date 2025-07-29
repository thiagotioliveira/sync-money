package dev.thiagooliveira.syncmoney.infra.exception;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessLogicException.class)
  public ResponseEntity<dev.thiagooliveira.syncmoney.infra.user.api.dto.Error>
      handleBusinessLogicException(BusinessLogicException ex) {
    int statusCode = ex.errorCode();
    return ResponseEntity.status(statusCode)
        .body(
            new dev.thiagooliveira.syncmoney.infra.user.api.dto.Error()
                .code(statusCode)
                .message(ex.getMessage()));
  }
}
