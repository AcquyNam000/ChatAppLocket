package com.example.chatapplocket;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chatApp.db";
    private static final int DATABASE_VERSION = 1;

    // Tạo câu lệnh tạo bảng
    private static final String CREATE_TABLE_HINHANH = "CREATE TABLE IF NOT EXISTS HinhAnh (" +
            "image_column BLOB, " +  // Cột chứa hình ảnh (kiểu BLOB)
            "email TEXT" +           // Cột chứa email
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HINHANH); // Tạo bảng HinhAnh
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HinhAnh"); // Xóa bảng cũ nếu có
        onCreate(db);  // Tạo lại bảng mới
    }

    // Hàm thêm hình ảnh vào bảng
    public boolean insertImage(byte[] image, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO HinhAnh (image_column, email) VALUES (?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1, image);  // Chèn hình ảnh vào cột image_column
        statement.bindString(2, email); // Chèn email vào cột email
        long result = statement.executeInsert();
        db.close();
        return result != -1;  // Trả về true nếu thành công
    }

    // Hàm lấy hình ảnh theo email
    public byte[] getImageByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT image_column FROM HinhAnh WHERE email = ? LIMIT 1", new String[]{email});
        byte[] image = null;

        if (cursor != null && cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndex("image_column");
            if (imageIndex != -1) {
                image = cursor.getBlob(imageIndex);
            }
        }
        if (cursor != null) cursor.close();
        db.close();
        return image;
    }

    // Hàm kiểm tra xem bảng có tồn tại cột email hay không
    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM HinhAnh WHERE email = ?", new String[]{email});
        boolean exists = cursor.moveToFirst();
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }
}

