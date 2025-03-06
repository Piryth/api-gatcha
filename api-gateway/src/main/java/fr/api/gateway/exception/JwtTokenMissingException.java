package fr.api.gateway.exception;

public class JwtTokenMissingException extends RuntimeException {

    public JwtTokenMissingException(final String message) {
        super(message);
    }

}
