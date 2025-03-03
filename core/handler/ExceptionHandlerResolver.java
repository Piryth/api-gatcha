package handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerResolver {

    @ExceptionHandler({})
    public void handleUnauthenticatedException(Exception ex) {

    }

}
