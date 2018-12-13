package com.payline.payment.samsung.pay.exception;

/**
 * Thrown when the incoming request is invalid or incomplete.
 */
public class InvalidRequestException extends Exception {

    public InvalidRequestException(String message ){
        super( message );
    }
    
}
