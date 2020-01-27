package br.com.mmgestor.service;

public class UsernameAlreadyUsedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -142444582910773800L;

    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }

}
