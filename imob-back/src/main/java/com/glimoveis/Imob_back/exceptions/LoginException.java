package com.glimoveis.Imob_back.exceptions;

public class LoginException extends RuntimeException{

    public LoginException(){
        super("Você precisa estar logado para realizar essa ação.");
    }
}
