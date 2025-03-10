package fr.imt.gateway.exception;

public class JwtTokenMalformedException extends RuntimeException{

    public JwtTokenMalformedException(final String message) {
        super(message);
    }

}
