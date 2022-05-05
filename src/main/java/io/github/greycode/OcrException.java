package io.github.greycode;

public class OcrException extends RuntimeException {
  private static final long serialVersionUID = 7470952907051382949L;

  public OcrException(String err) {
    super(err);
  }
  public OcrException(Throwable t) {
    super(t);
  }
}
