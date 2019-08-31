package com.example.socket_connection;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.example.socket_connection.Method;
import com.example.socket_connection.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.io.Reader;
import java.nio.file.Paths;


public class Http_Controller extends Controller{
    public static String protocolVersion = "HTTP/1.1";
    public static Context context;
    public Http_Controller(Context context){
        this.context = context;
    }
    @Override
    public Response doGet(Request request){//現在doGet,doPostは適当なresponseを返しているため、後でしっかり直すこと。
        //Path target = Paths.get();
        AssetManager assetManager = context.getAssets();
        String html = "";

        Response response;
        try{
            response = new Response(protocolVersion,Status.OK);

            InputStream inputStream = assetManager.open("HelloWorld.html");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                html += str + "\n";
            }
            if(inputStream != null){
                inputStream.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            response.setBody(html.getBytes());
            response.addHeaderField("Content-Length",Integer.toString(response.getBody().length));
            String ext = "html";
            String contentType = response.extensionToContentType(ext);

            response.addHeaderField("Content-Type",contentType);


        }catch (IOException e){
            e.printStackTrace();
            response = new Response(protocolVersion, Status.NOT_FOUND);
            response.setBody("".getBytes());
            response.addHeaderField("Content-Length", Integer.toString(response.getBody().length));
        }
        return response;
    }
    @Override
    public Response doPost(Request request){
        Response response = new Response("default",Status.BAD_REQUEST);
        return response;
    }
}
