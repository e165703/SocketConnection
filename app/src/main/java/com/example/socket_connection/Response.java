package com.example.socket_connection;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Response {
    String version;
    Status status;
    protected Map<String, String> headers;
    protected byte[] body;
    private static final Map<String,String> mimetypes;
    static {
        mimetypes = new HashMap<>();
        mimetypes.put("html","text/html");
        mimetypes.put("css", "text/css");
        mimetypes.put("js", "application/js");
        mimetypes.put("png", "image/png");
        mimetypes.put("txt", "text/plain");
    }
    public Response(String version, Status status){
        super();
        this.version = version;
        this.status = status;
        this.headers = new HashMap<>();
        this.body = new byte[0];
    }
    public static String extensionToContentType(String ext){
        String extension = "";
        switch (ext){
            case "html":
                extension = mimetypes.get("html");
                break;
            case "css":
                extension = mimetypes.get("css");
                break;
            case "js":
                extension = mimetypes.get("js");
                break;
            case "png":
                extension = mimetypes.get("png");
                break;
            case "txt":
                extension = mimetypes.get("txt");
                break;
        }
        return extension;
    }
    public void addHeaderField(String name, String value){
        this.headers.put(name, value);
    }
    public Map<String, String> getHeaders(){
        return headers;
    }
    public void setBody(byte[] body){
        this.body = body;
    }
    public byte[] getBody(){
        return body;
    }
    public String getVersion(){
        return version;
    }
    public int getStatusCode(){
        return status.getCode();
    }
    public String getReasonPhrase(){
        return status.getReasonPhrase();
    }
    public String getStartLine(){
        return version + " " + getStatusCode() + " " + getReasonPhrase();
    }
}
