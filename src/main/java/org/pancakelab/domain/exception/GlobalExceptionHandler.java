package org.pancakelab.domain.exception;


import lombok.extern.slf4j.Slf4j;
@Slf4j
public class GlobalExceptionHandler {

    public static void handle(Exception e) {
        if (e instanceof PancakeLabException) {
            PancakeLabException pancakeLabException = (PancakeLabException) e;
            logPancakeLabException(pancakeLabException);
        } else {
            logUnexpectedException(e);
        }
    }
    private static void logPancakeLabException(PancakeLabException e) {
        String errorCode = e.getExceptionEnum().getPancakeLabErrorCode();
        log.error(String.format("[%s] PancakeLabException:", errorCode), e);
    }

    private static void logUnexpectedException(Exception e) {
        log.error(String.format("An unexpected error occurred: %s", e.getMessage()), e);
    }
}

