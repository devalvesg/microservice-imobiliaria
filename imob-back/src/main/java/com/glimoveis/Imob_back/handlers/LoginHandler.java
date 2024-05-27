package com.glimoveis.Imob_back.handlers;

import com.glimoveis.Imob_back.exceptions.ImmobilesNotFoundException;
import com.glimoveis.Imob_back.exceptions.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LoginHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(LoginException.class)
    public String handler(LoginException ex){
        return ex.getMessage();
    }
}
