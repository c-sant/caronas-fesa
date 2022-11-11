package com.carona.exceptions;

public class InvalidFormatPhoneNumberException extends Exception{
    
    public InvalidFormatPhoneNumberException(String message){
        super(message);
    }
}