package com.example.caatulgupta.chatbot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MessagesViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public MessagesViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
    }

}
