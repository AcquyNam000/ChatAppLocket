package com.example.chatapplocket.THI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplocket.R;

public class AddSanPhamActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sanpham);

        dbHelper = new DatabaseHelper(this);

        EditText etTenSanPham = findViewById(R.id.et_tenSanPham);
        EditText etSoLuong = findViewById(R.id.et_soLuong);
        EditText etGia = findViewById(R.id.et_gia);
        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(view -> {
            String tenSanPham = etTenSanPham.getText().toString();
            int soLuong = Integer.parseInt(etSoLuong.getText().toString());
            double gia = Double.parseDouble(etGia.getText().toString());

            if (dbHelper.insertSanPham(tenSanPham, soLuong, gia)) {
                Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SanPhamListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}