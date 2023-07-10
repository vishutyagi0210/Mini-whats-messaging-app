package com.example.miniwhatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniwhatsapp.ChatDetailActivity;
import com.example.miniwhatsapp.Models.User;
import com.example.miniwhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclarChatsAdapter extends RecyclerView.Adapter<RecyclarChatsAdapter.ViewHolder> {

    ArrayList<User> userList;
    Context context;



    public RecyclarChatsAdapter(Context context , ArrayList<User> userList){
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_recycler_view_sample , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        Picasso.get().load(user.getProfilePicture()).placeholder(R.drawable.whatsapp).into(holder.profileImage);
        holder.profileName.setText(user.getUserName());

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUserId())
                .orderByChild("timeStem")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot snapshot1: snapshot.getChildren()){
                                holder.LastMessage.setText(snapshot1.child("message").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.userItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ChatDetailActivity.class);
                intent.putExtra("userId" , user.getUserId());
                intent.putExtra("userName",user.getUserName());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView profileName , LastMessage;
        ImageView profileImage;
        LinearLayout userItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.Sample_profile_image);
            profileName = itemView.findViewById(R.id.Sample_Profile_Name);
            LastMessage = itemView.findViewById(R.id.Sample_profile_last_message);
            userItem = itemView.findViewById(R.id.UserItem);
        }
    }
}
