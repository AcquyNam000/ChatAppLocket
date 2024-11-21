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
    TextView reciverName;
    CardView sebdtb;
    EditText textmsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String senderImg , reciverIImg;
    String senderRoom , reciverRoom;
    RecyclerView mmessengersApdapter;
    ArrayList<msgModelclass> msgArrayList;
    messagesAdapter messagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);

        mmessengersApdapter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessengersApdapter.setLayoutManager(linearLayoutManager);
        messagesAdapter = new messagesAdapter(chatWin.this,msgArrayList);
        mmessengersApdapter.setAdapter(messagesAdapter);

       recivername = getIntent().getStringExtra("name");
       reciverimg = getIntent().getStringExtra("pic");
       reciveruid = getIntent().getStringExtra("uid");

       msgArrayList = new ArrayList<>();

       sebdtb = findViewById(R.id.sendbtnn);
       textmsg = findViewById(R.id.textmsg);

       profileimgg = findViewById(R.id.profileimgg);
       reciverName = findViewById(R.id.recivername);

       profileimgg.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/chatapplocket.appspot.com/o/"));
       reciverName.setText(""+recivername);

       DatabaseReference reference = database.getReference().child("chats").child(firebaseAuth.getUid()+reciveruid);
       DatabaseReference chatrefence = database.getReference().child("user").child(senderRoom).child("messages");

       chatrefence.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               msgArrayList.clear();
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   msgModelclass msgModelclass = dataSnapshot.getValue(msgModelclass.class);
                   msgArrayList.add(msgModelclass);
               }
               messagesAdapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = snapshot.child("profilepic").getValue().toString();
                reciverIImg = reciverIImg;
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
       Senderuid = FirebaseAuth.getInstance().getUid();
       senderRoom = Senderuid + reciveruid;
       reciverRoom = reciveruid + Senderuid;

       sebdtb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String msg = textmsg.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(chatWin.this, "Vui lòng nhập tin nhắn", Toast.LENGTH_SHORT).show();
                }
                textmsg.setText("");
                Date date = new Date();
                msgModelclass msgModel = new msgModelclass(msg,Senderuid,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child("senderRoom").child(msg).push().setValue(msgModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child("reciverRoom").child("messages").push().setValue(msgModel).addOnCompleteListener(new OnCompleteListener<Void>() {
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