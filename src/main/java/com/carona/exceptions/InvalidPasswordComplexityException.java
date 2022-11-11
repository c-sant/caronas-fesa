package com.carona.exceptions;

public class InvalidPasswordComplexityException extends Exception{
    
    public InvalidPasswordComplexityException(String message){
        super(message);
    }
}
