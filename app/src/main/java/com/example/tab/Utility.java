package com.example.tab;

import com.google.gson.Gson;

import org.json.JSONObject;

public class Utility {
    public static Data handleDataResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            return new Gson().fromJson(String.valueOf(jsonObject),Data.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
