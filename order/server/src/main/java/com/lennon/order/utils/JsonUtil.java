package com.lennon.order.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * json转对象
     */
    public static Object fromJson(String string,Class classType){
        try{
            return objectMapper.readValue(string,classType);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     */
    public static Object fromJson(String string,TypeReference typeReference){
        try{
            return objectMapper.readValue(string,typeReference);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
