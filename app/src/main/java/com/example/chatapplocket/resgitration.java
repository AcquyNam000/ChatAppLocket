package com.example.chatapplocket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class resgitration extends AppCompatActivity {
    TextView loginbut;
    EditText rg_username , rg_email , rg_password , rg_repassword;
    Button rg_singup;
    ImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imgURL;
    String imageuri;
    String emailPattern  = "[a-zA-z0-9._-]+@[a-z]+\\.[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resgitration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        loginbut = findViewById(R.id.loginbutton);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_profileImg = findViewById(R.id.profilerg0);
        rg_singup = findViewById(R.id.signupbutton);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

       loginbut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(resgitration.this,login.class);
               startActivity(intent);
               finish();
           }
       });

       rg_singup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = rg_username.getText().toString();
               String email = rg_email.getText().toString();
               String Password = rg_password.getText().toString();
               String cPassword = rg_repassword.getText().toString();
               String status = "Chào bạn tôi đang sử dụng chương trình ";

               if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                       || TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)){
                   progressDialog.dismiss();
                   Toast.makeText(resgitration.this,"Làm ơn hãy điền hết dữ liệu ",Toast.LENGTH_SHORT).show();
               }else if( !email.matches(emailPattern)){
                   progressDialog.dismiss();
                   rg_email.setError("Hãy điền đúng kiểu của email ");
               } else if (Password.length() < 6 ) {
                   progressDialog.dismiss();
                   rg_password.setError("Mật khẩu phải có ít nhất 6 kí tự ");
               } else if ( !Password.equals(cPassword) ) {
                   progressDialog.dismiss();
                   rg_password.setError("Hai mã mật khẩu không giống nhau");
               } else {
                   auth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()) {
                               String id = task.getResult().getUser().getUid();
                               DatabaseReference reference = database.getReference().child("user").child(id);
                               ////// đoạn code tự biên
                           User u = new User(cPassword,email,name,id,status);
                           reference.setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       progressDialog.dismiss();
                                       Intent intent = new Intent(resgitration.this,MainActivity.class);
                                       startActivity(intent);
                                       finish();
                                   } else {
                                       Toast.makeText(resgitration.this,"Tạo tài khoản thất bại",Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                           }}});}}});}}
                               /////đoạn code tự biên
//                               StorageReference storageReference = storage.getReference().child("Upload").child(id);

//                               if(imgURL != null){
//                                   storageReference.putFile(imgURL).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                       @Override
//                                       public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                           if(task.isSuccessful()){
//                                               storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                   @Override
//                                                   public void onSuccess(Uri uri) {
//                                                       imageuri = uri.toString();
//                                                       user u = new user(id,name,email,Password,cPassword, imageuri, status);
//                                                   }
//                                               });
//                                           } else {
//                                               String status = "tôi đang sử dụng chương trình";
//                                               imageuri = "https://www.facebook.com/photo/?fbid=1586496045466558&set=pb.100023185329715.-2207520000";
//                                               user u = new user(id,name,email,Password,cPassword,imageuri,status);
//                                               reference.setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                   @Override
//                                                   public void onComplete(@NonNull Task<Void> task) {
//                                                       if(task.isSuccessful()){
//                                                           Intent intent = new Intent(resgitration.this,MainActivity.class);
//                                                           startActivity(intent);
//                                                           finish();
//                                                       } else {
//                                                           Toast.makeText(resgitration.this,"Tạo tài khoản thất bại",Toast.LENGTH_SHORT).show();
//                                                       }
//                                                   }
//                                               });
//                                           }
//                                       }
//                                   });
//                               }
//
//                           }else {
//                               Toast.makeText(resgitration.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                           }
//                       }
//                   });
//               }
//           }
//       });
//
//
//       rg_profileImg.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Intent intent  = new Intent();
//               intent.setType("Image/*");
//               intent.setAction(Intent.ACTION_GET_CONTENT);
//               startActivityForResult(Intent.createChooser(intent,"Chọn một bức ảnh"),10);
//           }
//       });
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10) {
//            if (data != null) {
//                imgURL = data.getData();
//                rg_profileImg.setImageURI(imgURL);
//            }
//        }
//    }
//}