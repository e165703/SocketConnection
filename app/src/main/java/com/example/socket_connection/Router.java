package com.example.socket_connection;

import android.content.Context;

import com.example.socket_connection.Http_Controller;


public class Router {
    public static Router instance;
    public static Router getInstance(){
        if (instance == null){
            instance = new Router();
        }
        return instance;
    }
    private Router(){
        instance = this;
    }
    public Controller route(Context context){
        return new Http_Controller(context);
    }
}
