package fr.api.gateway.exception;

public class JwtTokenMalformedException extends RuntimeException{

    public JwtTokenMalformedException(final String message) {
        super(message);
    }

}
