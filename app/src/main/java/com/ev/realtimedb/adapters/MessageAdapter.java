package com.ev.realtimedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ev.realtimedb.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> 
{
    
    private List<Message> messages;
    
    public MessageAdapter(List<Message> Messages) {
        this.messages = Messages;
    }
    
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new MessageViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.user.setText( msg.getUser() );
        holder.text.setText( msg.getText() );
    }
    
    @Override
    public int getItemCount() {
        return messages.size();
    }
    
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView user, text;
        
        MessageViewHolder(@NonNull View itemView) 
        {
            super(itemView);
            user = itemView.findViewById(android.R.id.text1);
            text = itemView.findViewById(android.R.id.text2);
        }
    }
}
