package com.advertisementproject.zuulgateway.api.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseStatusExceptionHandler {

}
