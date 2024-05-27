package com.glimoveis.Imob_back.exceptions;

public class ImmobilesNotFoundException  extends RuntimeException {

    public ImmobilesNotFoundException(){
        super("Imóvel não encontrado em nossa base de dados.");
    }

}
