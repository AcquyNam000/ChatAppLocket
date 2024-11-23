package com.example.chatapplocket;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanlyHinhAnh.sqlite";
    private static final int DATABASE_VERSION = 1;
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory,DATABASE_VERSION, errorHandler);
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public <Cusor> Cusor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return (Cusor) database.rawQuery(sql,null);
    }

    public Boolean INSERT_HINHANH(byte[] hinh, String email) {
        SQLiteDatabase database = null;
        SQLiteStatement statement = null;
        try {
            // Mở cơ sở dữ liệu ở chế độ ghi
            database = getWritableDatabase();

            // Kiểm tra xem bảng đã có đủ cột chưa
            String sql = "INSERT INTO HinhAnh (HinhAnh,email) VALUES (?,?)"; // Chỉ định rõ cột
            statement = database.compileStatement(sql);

            // Ràng buộc các giá trị vào câu lệnh SQL
            statement.clearBindings();
            statement.bindBlob(1, hinh);  // Gán hình ảnh vào cột image_column
            statement.bindString(2, email);  // Gán email vào cột email

            // Thực thi lệnh SQL
            statement.executeInsert();
            return true;  // Trả về true khi thêm thành công
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
            return false;  // Trả về false nếu có lỗi
        } finally {
            // Đảm bảo đóng tài nguyên
            if (statement != null) {
                statement.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }

    public byte[] getSingleImage(String email) {
        byte[] imageData = null;
        SQLiteDatabase database = getReadableDatabase();

        if (database != null) {
            Cursor cursor = null;
            try {
                cursor = database.rawQuery("SELECT HinhAnh FROM HinhAnh WHERE email = ?",
                        new String[]{email});

                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("HinhAnh");
                    if (columnIndex != -1) {
                        imageData = cursor.getBlob(columnIndex);
                        Log.e("Database", "cot may: " + columnIndex);

                    }
                }
            } catch (Exception e) {
                Log.e("Database", "Error fetching image: " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return imageData;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE IF NOT EXISTS HinhAnh (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HinhAnh BLOB)";
        sqLiteDatabase.execSQL(createTable);

        // Thêm cột email sau khi tạo bảng
        try {
            sqLiteDatabase.execSQL("ALTER TABLE HinhAnh ADD COLUMN email NVARCHAR(255)");
        } catch (Exception e) {
            // Nếu cột đã tồn tại, sẽ bắn ra ngoại lệ
            Log.e("Database", "Column already exists or error adding column: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HinhAnh");
        onCreate(sqLiteDatabase);
    }
}