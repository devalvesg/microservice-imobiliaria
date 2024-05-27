package com.glimoveis.Imob_back.handlers;

import com.glimoveis.Imob_back.exceptions.BlockPropertiesException;
import com.glimoveis.Imob_back.exceptions.DeleteImmobileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DeleteImmobileHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(DeleteImmobileException.class)
    public String handler(DeleteImmobileException ex){
        return ex.getMessage();
    }
}
