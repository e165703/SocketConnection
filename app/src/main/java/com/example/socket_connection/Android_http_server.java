package com.example.socket_connection;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import org.apache.ftpserver.impl.IODataConnection;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.util.*;
import com.example.socket_connection.Router;

public class Android_http_server{
    Router router;
    public static final int PORT = 5000;
    public String result;
    static public Context context;
    public TextView textView;
    static public ServerSocket server;
    static public Socket socket;
    static public BufferedReader reader;
    public Android_http_server(Context context){
        this.context = context;
        this.router = Router.getInstance();
    }
    public String Request_message(){

        Response response;

        try {
            Log.d("b","bbb");
            server = new ServerSocket(PORT);
            socket = server.accept();
            String readline;
            result = "";
            reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            OutputStream out = socket.getOutputStream();
            readline = reader.readLine();
            while (readline != null){
                result = result +readline + "\n";
                if(reader.ready()){
                    readline = reader.readLine();
                }else{
                    readline = null;
                }
            }
            Log.d("c","ccc");
            Log.d("d",result);
            //ここにhttpリクエストを分解する処理を入れる
            Http_Request_Disassembly http_request_disassembly = new Http_Request_Disassembly();
            Request request = http_request_disassembly.Request_Disassembly(result);
            Controller controller = router.route(context);
            try {
                response = controller.handle(request);
                byte[] message = http_request_disassembly.serializeResponse(response);
                out.write(message);
            }catch (UnsupportedMethodException e){
                e.printStackTrace();
            }
            Log.d("version:",request.version);
            Log.d("target:",request.target);
            Log.d("method:",request.method.toString());
            Log.d("e","eeee");
            //output.close();
            //out.close();
            //socket.close();
            //server.close();
            //reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
    public void Server_stop(){
        try {
            server.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
