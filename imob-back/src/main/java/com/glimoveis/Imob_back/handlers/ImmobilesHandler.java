package com.glimoveis.Imob_back.handlers;

import com.glimoveis.Imob_back.exceptions.BlockPropertiesException;
import com.glimoveis.Imob_back.exceptions.ImmobilesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ImmobilesHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(ImmobilesNotFoundException.class)
    public String handler(ImmobilesNotFoundException ex){
        return ex.getMessage();
    }
}
