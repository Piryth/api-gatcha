package fr.imt.gateway.configuration.exceptions;

public class NoJwtSecretFilePathException extends RuntimeException {

    public NoJwtSecretFilePathException() {
        super("No JWT secret file path provided.");
    }

}
