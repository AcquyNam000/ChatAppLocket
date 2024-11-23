package com.example.chatapplocket;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {
    MainActivity mainActivity;
    ArrayList<User> userArrayList;
    public UserAdapter(MainActivity mainActivity, ArrayList<User> userArrayList) {
        this.mainActivity = mainActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Viewholder holder, int position) {
        User u =  userArrayList.get(position);
        holder.username.setText(u.userName);
        holder.userstatus.setText(u.status);
//        Picasso.get().load(u.profilepic).into(holder.userImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(mainActivity,chatWin.class);
            intent.putExtra("name",u.getUserName());
            intent.putExtra("pic",u.getProfilepic());
            intent.putExtra("uid",u.getUserId());
            mainActivity.startActivity(intent);
        }});
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView username;
        TextView userstatus;

        public Viewholder(@NonNull View itemview ){
            super(itemview);
            userImg = itemview.findViewById(R.id.userImg);
            username = itemview.findViewById(R.id.username);
            userstatus = itemview.findViewById(R.id.userstatus);

        }
    }
}
