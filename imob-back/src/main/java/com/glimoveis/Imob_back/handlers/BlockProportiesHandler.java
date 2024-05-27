package com.glimoveis.Imob_back.handlers;

import com.glimoveis.Imob_back.exceptions.BlockPropertiesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BlockProportiesHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(BlockPropertiesException.class)
    public String handler(BlockPropertiesException ex){
        return ex.getMessage();
    }
}
