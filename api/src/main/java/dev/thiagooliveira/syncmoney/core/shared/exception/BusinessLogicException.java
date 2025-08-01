package dev.thiagooliveira.syncmoney.core.shared.exception;

public class BusinessLogicException extends RuntimeException {
  private final BusinessLogicCodeError errorCode;

  public BusinessLogicException(String message) {
    super(message);
    this.errorCode = BusinessLogicCodeError.GENERIC_ERROR;
  }

  private BusinessLogicException(BusinessLogicCodeError errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public static BusinessLogicException notAuthorized(String message) {
    return new BusinessLogicException(BusinessLogicCodeError.NOT_AUTHORIZED, message);
  }

  public static BusinessLogicException notFound(String message) {
    return new BusinessLogicException(BusinessLogicCodeError.NOT_FOUND, message);
  }

  public static BusinessLogicException badRequest(String message) {
    return new BusinessLogicException(BusinessLogicCodeError.GENERIC_ERROR, message);
  }

  public int errorCode() {
    return this.errorCode.code;
  }

  public enum BusinessLogicCodeError {
    GENERIC_ERROR(400),
    NOT_FOUND(404),
    NOT_AUTHORIZED(403);

    private final int code;

    private BusinessLogicCodeError(int code) {
      this.code = code;
    }
  }
}
