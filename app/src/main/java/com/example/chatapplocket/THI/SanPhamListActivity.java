package com.example.chatapplocket.THI;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplocket.R;

import java.util.ArrayList;

public class SanPhamListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham_list);

        dbHelper = new DatabaseHelper(this);
        ListView listView = findViewById(R.id.listView);

        ArrayList<String> sanPhamList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSanPham();

        if (cursor.moveToFirst()) {
            do {
                String item = "ID: " + cursor.getInt(0) +
                        ", Tên: " + cursor.getString(1) +
                        ", Số lượng: " + cursor.getInt(2) +
                        ", Giá: " + cursor.getDouble(3);
                sanPhamList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sanPhamList);
        listView.setAdapter(adapter);
    }
}