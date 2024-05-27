package com.glimoveis.Imob_back.exceptions;

public class DeleteImmobileException extends RuntimeException{
    public DeleteImmobileException(){
        super("Erro ao deletar o imóvel, você não possui permissão para deletar esse imóvel.");
    }
}
