package dev.thiagooliveira.syncmoney.infra.shared.exception;

import dev.thiagooliveira.syncmoney.core.shared.exception.BusinessLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<dev.thiagooliveira.syncmoney.infra.user.api.dto.Error>
      handleBusinessLogicException(Exception ex) {
    logger.error("Unexpected error", ex);
    int statusCode = 500;
    return ResponseEntity.status(statusCode)
        .body(
            new dev.thiagooliveira.syncmoney.infra.user.api.dto.Error()
                .code(statusCode)
                .message("some internal error, please try again later"));
  }
}
