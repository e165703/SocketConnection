package com.example.socket_connection;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.Map;
public class Request {
    Method method;
    String target;
    String version;
    protected Map<String,String> headers;
    protected byte[] body;

    public Request(Method method, String target, String version){
        super();
        this.method = method;
        this.target = target;
        this.version = version;
        this.headers = new HashMap<>();
        this.body = new byte[0];
    }

    public Method getMethod(){
        return method;
    }

    public String getTarget(){
        return target;
    }

    public String getVersion(){
        return version;
    }

    public String getStartLine(){
        return (method.toString()+" "+target+" "+version);
    }

    public void addHeaderField(String name, String value){
        this.headers.put(name,value);
    }

    public Map<String,String> getHeaders(){
        return headers;
    }

    public void setBody(byte[] body){
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

}
