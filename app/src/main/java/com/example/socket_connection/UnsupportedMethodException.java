package com.example.socket_connection;

public class UnsupportedMethodException extends Exception{
    private static final long serialVersionUID =1L;

    public UnsupportedMethodException(String method){
        super("Unknown HTTP method: "+ method);
    }
}
