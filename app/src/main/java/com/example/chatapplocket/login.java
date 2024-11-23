package com.example.chatapplocket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.text.TextUtils;

public class login extends AppCompatActivity {
    Button button , btnDangki;
    EditText email , password;
    FirebaseAuth auth;
    String emailPattern  = "[a-zA-z0-9._-]+@[a-z]+\\.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    auth = FirebaseAuth.getInstance();
    button = findViewById(R.id.logButton);
    email = findViewById(R.id.editTextLogEmail);
    password = findViewById(R.id.editTextLogPassword);
    btnDangki = findViewById(R.id.dangkiBTN);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String Email = email.getText().toString();
            String Pass = password.getText().toString();
            if((TextUtils.isEmpty(Email))){
                Toast.makeText(login.this,"Enter the Email ", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(Pass)){
                Toast.makeText(login.this,"Enter the Password",Toast.LENGTH_SHORT).show();
            } else if (!Email.matches(emailPattern)){
                email.setError("chưa viết đúng kí tự của email ");
           } else if(password.length()<6) { password.setError(" mật khẩu phải đủ nhiều hơn 6 kí tự ");
                Toast.makeText(login.this,"mật khẩu nhiều hơn 6 kí tự ",Toast.LENGTH_SHORT).show();
}else  {
            auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        try {
                            Intent intent = new Intent(login.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }catch (Exception e) {
                            Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(login.this,task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                }
            });}
        }
    });
    btnDangki.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(login.this,resgitration.class);
            startActivity(intent);
            finish();
        }
    });
    }


}