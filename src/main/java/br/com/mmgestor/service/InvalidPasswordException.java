package br.com.mmgestor.service;

public class InvalidPasswordException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6614781929196682349L;

    public InvalidPasswordException() {
        super("Incorrect password");
    }

}
