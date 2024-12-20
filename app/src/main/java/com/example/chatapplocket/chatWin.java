package com.example.chatapplocket;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class chatWin extends AppCompatActivity {

    String reciverimg, recivername, reciveruid , Senderuid ;

    ImageView profileimgg;
    TextView reciverNName;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    public static String senderImg , reciverIImg;

    CardView sebdtb;
    EditText textmsg;

    String senderRoom , reciverRoom;
    RecyclerView messagesAdapter;
    ArrayList<msgModelclass> msgArrayList;
    messagesAdapter mmessagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recivername = getIntent().getStringExtra("name");
        reciverimg = getIntent().getStringExtra("pic");
        reciveruid = getIntent().getStringExtra("uid");

        msgArrayList = new ArrayList<>();

        sebdtb = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);

        reciverNName = findViewById(R.id.recivername);
        profileimgg = findViewById(R.id.profileimgg);
        messagesAdapter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messagesAdapter.setLayoutManager(linearLayoutManager);
        mmessagesAdapter = new messagesAdapter(chatWin.this,msgArrayList);
        messagesAdapter.setAdapter(mmessagesAdapter);

        //pi
        reciverNName.setText("" + recivername);

        Senderuid = firebaseAuth.getUid();
        senderRoom = Senderuid + reciveruid;
        reciverRoom = reciveruid + Senderuid;

       DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
       DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

       chatreference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               msgArrayList.clear();
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   msgModelclass messages = dataSnapshot.getValue(msgModelclass.class);
                   msgArrayList.add(messages);
               }
               mmessagesAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = snapshot.child("profilepic").getValue().toString();
                reciverIImg = reciverimg;
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


       sebdtb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String msg = textmsg.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(chatWin.this, "Vui lòng nhập tin nhắn", Toast.LENGTH_SHORT).show();
                    return;
                }

                textmsg.setText("");
                Date date = new Date();
                msgModelclass messagess = new msgModelclass(msg,Senderuid,date.getTime());

                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(reciverRoom)
                                .child("messages")
                                .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(chatWin.this, "Gửi tin nhắn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });;
           }});
    }
}