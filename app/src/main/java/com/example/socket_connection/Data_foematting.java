package com.example.socket_connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.PrintWriter;
import java.io.IOException;



public class Data_foematting {
    public Map<String,String> map = new HashMap<>();
    public List<String> list = new ArrayList<>();
    public void Formatting(String before_data){
        String[] Tak = before_data.split("\n");
        for(int i=0;i < Tak.length;i++){
            list.add(Tak[i]);
        }

    }
    public Map<String,String> getMap(){
        return map;
    }
}
