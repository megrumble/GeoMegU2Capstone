package com.trilogyed.inventoryservice.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {

    }

    public NotFoundException(String message) {
        super(message);
    }
}
