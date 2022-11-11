package com.carona.exceptions;

public class InvalidFormatStudentIdException extends Exception{
    
    public InvalidFormatStudentIdException(String message){
        super(message);
    }
}