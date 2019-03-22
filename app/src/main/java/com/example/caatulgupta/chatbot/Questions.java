package com.example.caatulgupta.chatbot;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Questions {

    String type;
    String text;
    @SerializedName("items")
    ArrayList<Items> items = new ArrayList<>();
    Extras extras;

}

class Items {

    String id;
    String name;
    ArrayList<Choices> choices = new ArrayList<>();

}

class Choices {

    String id;
    String label;

}
