package com.example.caatulgupta.chatbot;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatbotService {

//    @POST("diagnosis")
//    Call<> ge


    @GET("conditions")
    Call<ArrayList<Conditions>> getConditions();


    @GET("conditions/{id}")
    Call<Conditions> getCondition(@Path("id") String id);


    @POST("parse")
    Call<Parse> postMessageForReply(@Body RequestBody body);


    @POST("diagnosis")
    Call<Diagnosis> postDiagnosis(@Body RequestBody body);

}
