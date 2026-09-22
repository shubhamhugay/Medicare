package com.medicare.exception;

public class PrescriptionConflictException
        extends RuntimeException {

    public PrescriptionConflictException(
            String message) {

        super(message);
    }
}