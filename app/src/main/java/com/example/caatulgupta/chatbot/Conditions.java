package com.example.caatulgupta.chatbot;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Conditions {

    String id;
    String name;
    @SerializedName("common_name")
    String commonName;
    float probability;
    @SerializedName("sex_filter")
    String sexFilter;
    ArrayList<String> categories;
    String prevalence;
    String acuteness;
    String severity;
    Extras extras;
    @SerializedName("triage_level")
    String triageLevel;

}

class Extras{
    String hint;
    @SerializedName("icd10_code")
    String code;
}
