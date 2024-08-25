package org.pancakelab.domain.exception;

import lombok.Getter;

/**
 * Common runtime exception, that can be thrown by any part of Pancake Lab.
 */
@Getter

public class PancakeLabException extends RuntimeException {

  public PancakeLabException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public PancakeLabException(PancakeLabExceptionEnum exceptionEnum, String message, Throwable throwable) {
    super(message, throwable);
    this.exceptionEnum = exceptionEnum;
    this.customMessage = message;
  }

  public PancakeLabException(PancakeLabExceptionEnum exceptionEnum, String message) {
    super(message);
    this.exceptionEnum = exceptionEnum;
    this.customMessage = message;
  }
  private PancakeLabExceptionEnum exceptionEnum;

  private String customMessage;


}
