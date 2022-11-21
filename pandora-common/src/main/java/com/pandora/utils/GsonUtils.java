package com.pandora.utils;

import com.google.gson.Gson;

public class GsonUtils {

    private static Gson gson=new Gson();


    public static <T> String toString(T t){
       return gson.toJson(t);
    }
}
