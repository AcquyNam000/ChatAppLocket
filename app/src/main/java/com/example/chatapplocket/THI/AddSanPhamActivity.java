package com.example.chatapplocket.THI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapplocket.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSanPhamActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sanpham);

        databaseReference = FirebaseDatabase.getInstance().getReference("SanPham");

        EditText etTenSanPham = findViewById(R.id.et_tenSanPham);
        EditText etSoLuong = findViewById(R.id.et_soLuong);
        EditText etGia = findViewById(R.id.et_gia);
        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(view -> {
            String tenSanPham = etTenSanPham.getText().toString();
            int soLuong = Integer.parseInt(etSoLuong.getText().toString());
            double gia = Double.parseDouble(etGia.getText().toString());

            String id = databaseReference.push().getKey();
            SanPham sanPham = new SanPham(id, tenSanPham, soLuong, gia);

            if (id != null) {
                databaseReference.child(id).setValue(sanPham).addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, SanPhamListActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(e ->
                        Toast.makeText(this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}