package com.example.chatapplocket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class setting extends AppCompatActivity {
    ImageView setprofile;
    EditText setname, setstatus;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String email , password;
    Button donebut ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setprofile = findViewById(R.id.settingprofile);
        setname = findViewById(R.id.settingname);
        setstatus = findViewById(R.id.settingstatus);
        donebut = findViewById(R.id.donebutt);
        DatabaseReference reference =  database.getReference().child("user").child(auth.getUid());
         ActivityResultLauncher<Intent> imagePickerLauncher;
         Database dbSqlite = new Database(this,"Quanly.sqlite",null,1);

//        imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        Uri selectedImageUri = result.getData().getData();
//                        if (selectedImageUri != null) {
//                            try {
//                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
//                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                                setprofile.setImageBitmap(bitmap);
//                                byte[] imageBytes = imageToByteArray(bitmap);
//                                // Lưu vào SQLite
//                                boolean isInserted = false;
//                                isInserted = dbSqlite.INSERT_HINHANH(imageBytes,);
//                                if (isInserted) {
//                                    Toast.makeText(this, "Lưu ảnh thành công!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(this, "Lưu ảnh thất bại!", Toast.LENGTH_SHORT).show();
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//        setprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"));
//            }
//        });

        donebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = setname.getText().toString();
                String Status = setstatus.getText().toString();
                User users = new User(password, email, name, auth.getUid(), Status);
                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(setting.this, "Data Is save ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(setting.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(setting.this, "Some thing went romg", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}