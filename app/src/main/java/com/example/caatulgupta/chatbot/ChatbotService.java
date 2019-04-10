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

    @Headers({"App-id: 5be77a36", "App-Key: 76e24b650eee585d24913756cfa58f32"})
    @GET("conditions")
    Call<ArrayList<Conditions>> getConditions();

    @Headers({"App-id: 5be77a36", "App-Key: 76e24b650eee585d24913756cfa58f32"})
    @GET("conditions/{id}")
    Call<Conditions> getCondition(@Path("id") String id);

    @Headers({"App-id: 5be77a36", "App-Key: 76e24b650eee585d24913756cfa58f32"})
    @POST("parse")
    Call<Parse> postMessageForReply(@Body RequestBody body);

    @Headers({"App-id: 5be77a36", "App-Key: 76e24b650eee585d24913756cfa58f32"})
    @POST("diagnosis")
    Call<Diagnosis> postDiagnosis(@Body RequestBody body);

}
