package com.example.chatapplocket.THI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SanPhamDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SANPHAM = "SanPham";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEN = "tenSanPham";
    private static final String COLUMN_SOLUONG = "soLuong";
    private static final String COLUMN_GIA = "gia";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SANPHAM + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEN + " TEXT, "
                + COLUMN_SOLUONG + " INTEGER, "
                + COLUMN_GIA + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SANPHAM);
        onCreate(db);
    }

    public boolean insertSanPham(String tenSanPham, int soLuong, double gia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN, tenSanPham);
        values.put(COLUMN_SOLUONG, soLuong);
        values.put(COLUMN_GIA, gia);

        long result = db.insert(TABLE_SANPHAM, null, values);
        return result != -1; // Nếu insert thành công, kết quả sẽ khác -1
    }

    public Cursor getAllSanPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SANPHAM, null);
    }
}