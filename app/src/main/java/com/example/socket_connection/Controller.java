package com.example.socket_connection;
import com.example.socket_connection.Method;
import com.example.socket_connection.Request;
import com.example.socket_connection.Response;
import android.util.Log;

public abstract class Controller {
    public abstract Response doPost(Request request);

    public abstract Response doGet(Request request);


    public Response handle(Request request) throws UnsupportedMethodException {
        Method method = request.getMethod();
        Response response;
        switch (method) {
            case GET:
                response = doGet(request);
                break;
            case POST:
                response = doPost(request);
                break;
            default:
                throw new UnsupportedMethodException(method.toString());
        }

        return response;//本来、変数を定義し、returnする場合は、returnの前にその変数に対して初期化処理を行う必要がある。しかし、Exceptionをthrowする場合は行う必要がなくなる。謎
    }
}
