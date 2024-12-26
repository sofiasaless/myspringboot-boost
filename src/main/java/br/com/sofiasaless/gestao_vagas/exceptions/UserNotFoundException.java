package br.com.sofiasaless.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException () {
        super("User not found");
    }
    
}
