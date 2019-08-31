package com.example.socket_connection;

import android.util.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Http_Request_Disassembly {
    private static Pattern requestLinePattern = Pattern.compile("^(?<method>\\S+) (?<target>\\S+) (?<version>\\S+)$");
    private static Pattern headerPattern = Pattern.compile("^(?<name>\\S+):[ \\t]?(?<value>.+)[ \\t]?$");

    public Request Request_Disassembly(String result){
        String[] result_1 = result.split("\n");
        Request request = RequestLine(result_1[0]);
        RequestHeader(request,result_1);
        return request;
    }
    public Request RequestLine(String request){
        Matcher matcher = requestLinePattern.matcher(request);
        Method method;
        String target;
        String version;

        Method Default_method = Method.DEFAULT;
        String Default_target = "";
        String Default_version = "";

        Request request_1 = new Request(Default_method,Default_target,Default_version);
        if(!matcher.matches()){
            Log.d("Error:","ParseException");
        }

        method = Method.valueOf(matcher.group(1));
        target = matcher.group(2);
        version = matcher.group(3);
        request_1 = new Request(method,target,version);

        return  request_1;
    }
    public void RequestHeader(Request request,String[] header){
        for(int i=1;header.length>i;i++){
            Matcher matcher = headerPattern.matcher(header[i]);

            if(matcher.matches()){
                request.addHeaderField(matcher.group(1),matcher.group(2));
            }else {
                Log.d("Error:","ParseException"+" "+header[i]);
            }
        }
    }
    public void RequestBody(String[] body){

    }
    public static byte[] serializeResponse(Response response)throws IOException{
        ByteArrayOutputStream message = new ByteArrayOutputStream();

        String version =response.getVersion();
        Integer statuscode = response.getStatusCode();
        String reasonPhrase = response.getReasonPhrase();

        message.write((version + " " + statuscode + " " + reasonPhrase + "\n").getBytes());
        Log.d("Response:",version + " " + statuscode + " " + reasonPhrase + "\n");
        for (Map.Entry<String,String> field :response.getHeaders().entrySet()){
            message.write((field.getKey() + ": " + field.getValue() + "\n").getBytes());
            Log.d("Response",field.getKey() + ": " + field.getValue() + "\n");
        }
        message.write("\n".getBytes());
        message.write(response.getBody());

        return message.toByteArray();
    }
}
