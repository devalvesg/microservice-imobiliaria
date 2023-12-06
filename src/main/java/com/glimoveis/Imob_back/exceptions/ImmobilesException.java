package com.glimoveis.Imob_back.exceptions;

public class ImmobilesException  extends RuntimeException {

    public ImmobilesException(){
        super("Imóvel não encontrado em nossa base de dados");
    }

    public ImmobilesException(String message) {
        super(message);
    }
}
