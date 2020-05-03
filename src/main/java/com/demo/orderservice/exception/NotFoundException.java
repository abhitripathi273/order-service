package com.demo.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -3204705332368137965L;

    public NotFoundException(String message) {
        super(message);
    }
}
