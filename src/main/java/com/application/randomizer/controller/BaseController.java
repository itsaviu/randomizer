package com.application.randomizer.controller;

import com.application.randomizer.dto.ErrorResp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseController {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResp error(Exception ex) {
        return new ErrorResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
