package com.example.caatulgupta.chatbot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static ChatbotService service;

    public static Retrofit getRetrofit(){
        if(retrofit==null){


            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.infermedica.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
        }
        return retrofit;
    }

    public static ChatbotService getService(){
        if(service == null){
            service = getRetrofit().create(ChatbotService.class);
        }
        return service;
    }

}
