package com.example.miniwhatsapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.miniwhatsapp.Adapters.FragmentAdapter;
import com.example.miniwhatsapp.Adapters.RecyclarChatsAdapter;
import com.example.miniwhatsapp.Models.User;
import com.example.miniwhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chats extends Fragment {
    public Chats() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<User> userList;

    private FirebaseDatabase database;

    private FirebaseAuth firebaseAuth;
    RecyclarChatsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setDataIntoList();

        // initialising the variables.
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.RecyclerViewChats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclarChatsAdapter(getContext() , userList);
        recyclerView.setAdapter(adapter);

    }

    public void setDataIntoList(){

        database = FirebaseDatabase.getInstance();
        userList = new ArrayList<>();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    if (dataSnapshot.hasChild("profile picture")) {
                        String profilePictureUrl = dataSnapshot.child("profile picture").getValue(String.class);
                        user.setProfilePicture(profilePictureUrl);
                    }
                    userList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}