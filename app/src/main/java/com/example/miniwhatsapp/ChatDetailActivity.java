package com.example.miniwhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniwhatsapp.Adapters.MessagesApadter;
import com.example.miniwhatsapp.Models.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Toolbar toolbar;

    Button button;
    EditText sendThisMessageText;

    ArrayList<Messages> messagesArrayList;
    RecyclerView chatRecyclerView;
    MessagesApadter messagesApadter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        setIds();
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String senderId = firebaseAuth.getUid();
        String receiversId = getIntent().getStringExtra("userId").toString();
        String receiversName = getIntent().getStringExtra("userName").toString();

        String senderData = senderId + receiversId; //SYzLUWojFdX1cBCOAJeXBUgWT5p2SYzLUWojFdX1cBCOAJeXBUgWT5p2
        String receiverData = receiversId + senderId; //SYzLUWojFdX1cBCOAJeXBUgWT5p2SYzLUWojFdX1cBCOAJeXBUgWT5p2

        getSupportActionBar().setTitle(receiversName);

        messagesArrayList = new ArrayList<>();
        messagesApadter = new MessagesApadter(this , messagesArrayList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatRecyclerView.setAdapter(messagesApadter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = sendThisMessageText.getText().toString();
                Messages message1 = new Messages(senderId , message);
                message1.setTimeStem(new Date().getTime());
                sendThisMessageText.setText("");

                firebaseDatabase.getReference()
                        .child("chats")
                        .child(senderData)
                        .push()
                        .setValue(message1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                firebaseDatabase.getReference()
                                        .child("chats")
                                        .child(receiverData)
                                        .push()
                                        .setValue(message1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });

            }
        });

        firebaseDatabase.getReference().child("chats")
                .child(senderData)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesArrayList.clear();
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            Messages ms = snapshot1.getValue(Messages.class);
                            messagesArrayList.add(ms);
                        }
                        messagesApadter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



    }

    public void setIds(){
        toolbar = findViewById(R.id.ToolBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRecyclerView = findViewById(R.id.ChatDetailsRecyclerView);
        sendThisMessageText = findViewById(R.id.ChatDetailsEditText);
        button = findViewById(R.id.ChatDetailsSendMessageButton);
    }
}