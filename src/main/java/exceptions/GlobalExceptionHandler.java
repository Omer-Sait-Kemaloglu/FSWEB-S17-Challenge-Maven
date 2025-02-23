package exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseError> handleRuntimeException(RuntimeException ex) {
        log.error("An error occurred: " + ex.getMessage());
        return new ResponseEntity<>(new ApiResponseError(ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
