package br.com.mmgestor.service;

public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = -133146770836645869L;

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }

}
