package com.example.caatulgupta.chatbot;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Parse {

    ArrayList<Mentions> mentions;
    ArrayList<String> tokens;
    String obvious;

}

class Mentions{

    String id;
    String name;
    @SerializedName("common_name")
    String commonName;
    String orth;
    @SerializedName("choice_id")
    String choiceId;
    @SerializedName("head_position")
    int headPosition;

}
