package handler;

import exception.UnauthenticatedException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerResolver {

    private final static String UNAUTHENTICATED_MSG = "Error : unauthenticated user";

    @ExceptionHandler({UnauthenticatedException.class, FeignException.class})
    protected ResponseEntity<String> handleUnauthenticatedException(Exception ex) {
        log.error(UNAUTHENTICATED_MSG + " : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHENTICATED_MSG);
    }
}
