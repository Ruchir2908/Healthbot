package com.example.caatulgupta.chatbot;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView messagesRecyclerView;
    Button sendButton;
    EditText typingEditText;
    MessagesAdapter adapter;
    ArrayList<String> chat = new ArrayList<>();
    ArrayList<String> userMessages = new ArrayList<>();
    ArrayList<String> botMessages = new ArrayList<>();
    Parse parsedData = new Parse();
    ArrayList<Mentions> mentions = new ArrayList<>();
    ArrayList<String> conditionsMentionedId = new ArrayList<>();
    public String App_Id = "5be77a36";
    public String App_Key = "76e24b650eee585d24913756cfa58f32";
    Retrofit retrofit;
    ChatbotService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        sendButton = findViewById(R.id.sendButton);
        typingEditText = findViewById(R.id.typingEditText);
        sendButton = findViewById(R.id.sendButton);
        adapter = new MessagesAdapter(chat,this);
        messagesRecyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(manager);
//        textView = findViewById(R.id.textView);

        retrofit = ApiClient.getRetrofit();
        service = ApiClient.getService();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                String myMessage = typingEditText.getText().toString();
                Log.i("NEW","Message: "+myMessage);
                userMessages.add(myMessage);
                chat.add(myMessage);
                Log.i("NEW", "AL: "+chat.get(chat.size()-1));
//                adapter.notifyItemInserted(messages.size()-1);
                adapter.notifyDataSetChanged();
//                while(!myMessage.equals("Diagnoz")) {
                    String parseJson = "{ \"text\" : \"" + myMessage + "\"}";
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), parseJson);
                    Call<Parse> parseCall = service.postMessageForReply(body);
                    parseCall.enqueue(new Callback<Parse>() {
                        @Override
                        public void onResponse(Call<Parse> call, Response<Parse> response) {
//                        messages.add(response.body().mentions.get(0).commonName);
//                        adapter.notifyDataSetChanged();
                            parsedData = response.body();
                            Log.i("DEBUG", "Parsed data: " + parsedData.toString());
                            mentions.addAll(parsedData.mentions);
                            for (int i = 0; i < mentions.size(); i++) {
                                conditionsMentionedId.add(mentions.get(i).id);
                                Log.i("WAH", "CM SIZE: " + conditionsMentionedId.size());
                            }
                            if(conditionsMentionedId.size()!=0){
                                Log.i("WAH","CM SIZE: "+conditionsMentionedId.size());
                                String diagnosisJson = "{ \"sex\" : \"male\", \"age\" : \"18\" , \"evidence\" : [ { \"id\" : \""+conditionsMentionedId.get(0)+"\" , \"choice_id\" : \"present\" } ] }";
                                RequestBody diagnosisBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),diagnosisJson);
                                Call<Diagnosis> diagnosisCall = service.postDiagnosis(diagnosisBody);
                                diagnosisCall.enqueue(new Callback<Diagnosis>() {
                                    @Override
                                    public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                                        Log.i("WAH","Diagnosis: "+response.body().shouldStop);
                                        for(int i=0;i<response.body().question.items.size();i++){
                                            botMessages.add(response.body().question.text);
                                            chat.add(response.body().question.text);
                                            adapter.notifyDataSetChanged();
                                        }
//                                    messages.add(response.body().question.text);
//                                    adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<Diagnosis> call, Throwable t) {
                                        Log.i("WAH","ERROR: "+t.getMessage());
                                        chat.add(t.toString());
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Parse> call, Throwable t) {
                            chat.add(t.toString());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Naa", Toast.LENGTH_SHORT).show();
                        }
                    });
//                }
                StringBuilder diagnosisJson = new StringBuilder();
                diagnosisJson.append("{ \"sex\" : \"male\", \"age\" : \"18\" , \"evidence\" : [");
                for(int i=0;i<conditionsMentionedId.size();i++){
                    diagnosisJson.append(" { \"id\" : \""+conditionsMentionedId.get(i)+"\" , \"choice_id\" : \"present\" }");
                }
                diagnosisJson.append(" ] }");
//                String diagnosisJson = "{ \"sex\" : \"male\", \"age\" : \"18\" , \"evidence\" : [ { \"id\" : \""+conditionsMentionedId.get(0)+"\" , \"choice_id\" : \"present\" } ] }";
                RequestBody diagnosisBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),diagnosisJson.toString());
                Call<Diagnosis> diagnosisCall = service.postDiagnosis(diagnosisBody);
                diagnosisCall.enqueue(new Callback<Diagnosis>() {
                    @Override
                    public void onResponse(Call<Diagnosis> call, Response<Diagnosis> response) {
                        Log.i("WAH","Diagnosis: "+response.body().shouldStop);
                        for(int i=0;i<response.body().question.items.size();i++){
                            botMessages.add(response.body().question.text);
                            chat.add(response.body().question.text);
                            adapter.notifyDataSetChanged();
                            }
//                                   messages.add(response.body().question.text);
//                                    adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Diagnosis> call, Throwable t) {
                        Log.i("WAH","ERROR: "+t.getMessage());
                        chat.add(t.toString());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
