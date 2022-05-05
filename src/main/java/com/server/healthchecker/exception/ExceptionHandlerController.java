package com.server.healthchecker.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {Exception.class})
    public void handleBv3Error(HttpServletRequest req, Exception e) {
        // TODO : send ex notification to teams or slack
        log.error("APP_EXCEPTION : " + e);
    }
}
