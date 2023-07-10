package com.example.miniwhatsapp.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniwhatsapp.Models.Messages;
import com.example.miniwhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

public class MessagesApadter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<Messages> messages;
    private final int SENDER_VIEW_TYPE = 1;
    private final int RECEIVER_VIEW_TYPE = 2;


    public MessagesApadter(Context context , ArrayList<Messages> messagesArrayList){
        this.context = context;
        this.messages = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender , parent , false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver , parent , false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages1 = messages.get(position);

        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder) holder).sendersMessage.setText(messages1.getMessage());
            ((SenderViewHolder)holder).sendersTimeStem.setText(String.valueOf(getHoursMinutesAMPMFromTimestamp(messages1.getTimeStem())));
        }
        else{
            ((ReceiverViewHolder)holder).receiversMessage.setText(messages1.getMessage());
            ((ReceiverViewHolder)holder).receiversTimeStem.setText(String.valueOf(getHoursMinutesAMPMFromTimestamp(messages1.getTimeStem())));
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_TYPE;
        return RECEIVER_VIEW_TYPE;
    }

    public static String getHoursMinutesAMPMFromTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String amPm = (hours < 12) ? "AM" : "PM";
        return String.format("%d:%02d %s", hours, minutes, amPm);
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receiversMessage , receiversTimeStem;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiversMessage = itemView.findViewById(R.id.SampleReceiverMessageTextView);
            receiversTimeStem = itemView.findViewById(R.id.SampleReceiverTimeStemTextView);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView sendersMessage , sendersTimeStem;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendersMessage = itemView.findViewById(R.id.SampleSenderMessageTextView);
            sendersTimeStem = itemView.findViewById(R.id.SampleSenderTimeStemTextView);
        }
    }
}
