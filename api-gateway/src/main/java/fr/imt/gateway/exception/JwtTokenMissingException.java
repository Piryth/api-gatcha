package fr.imt.gateway.exception;

public class JwtTokenMissingException extends RuntimeException {

    public JwtTokenMissingException(final String message) {
        super(message);
    }

}
