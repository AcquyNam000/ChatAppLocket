package com.example.chatapplocket;

import static com.example.chatapplocket.chatWin.senderImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messagesAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<msgModelclass> msgArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECIVE = 2;

    public messagesAdapter(Context context, ArrayList<msgModelclass> msgArrayList) {
        this.context = context;
        this.msgArrayList = msgArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND ){
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderviewholder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false);
            return new reciverviewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass msgModelclass = msgArrayList.get(position);
        if(holder.getClass() == senderviewholder.class){
            senderviewholder viewHolder = (senderviewholder) holder;
            viewHolder.msgtxt.setText(msgModelclass.getMessage());
//            Picasso.get().load(senderImg).into(viewHolder.cricleImage);

        }else {
            reciverviewholder viewHolder = (reciverviewholder) holder;
            viewHolder.msgtxt.setText(msgModelclass.getMessage());
//            Picasso.get().load(reciverIImg).into(viewHolder.cricleImage);
        }
    }

    @Override
    public int getItemCount() {
        return msgArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass msgModelclass = msgArrayList.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(msgModelclass.getSenderId())){
            return ITEM_SEND;
        }else {
            return ITEM_RECIVE;
        }
    }

    class senderviewholder extends RecyclerView.ViewHolder{
        ImageView cricleImage;
        TextView msgtxt;
        public senderviewholder(@NonNull View itemView) {
            super(itemView);
            cricleImage = itemView.findViewById(R.id.profilerggg);
            msgtxt = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    class reciverviewholder extends RecyclerView.ViewHolder{
        ImageView cricleImage;
        TextView msgtxt;
        public reciverviewholder(@NonNull View itemView) {
            super(itemView);
            cricleImage = itemView.findViewById(R.id.pro);
            msgtxt = itemView.findViewById(R.id.recivertextset);
        }
    }

}
