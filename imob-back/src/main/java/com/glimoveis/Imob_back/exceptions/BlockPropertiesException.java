package com.glimoveis.Imob_back.exceptions;

public class BlockPropertiesException extends RuntimeException{

    public BlockPropertiesException(){
        super("O registro de novos imóveis está desativado, consulte o administrador da API para mais informações.");
    }
}
