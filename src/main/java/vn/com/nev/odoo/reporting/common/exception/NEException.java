package vn.com.nev.odoo.reporting.common.exception;

public class NEException extends RuntimeException {

  public NEException() {
    super();
  }

  public NEException(String message) {
    super(message);
  }

  public NEException(String message, Throwable cause) {
    super(message, cause);
  }

  public NEException(Throwable cause) {
    super(cause);
  }
}
