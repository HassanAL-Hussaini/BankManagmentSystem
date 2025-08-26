package com.example.bankmanagmentsystem.API;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
