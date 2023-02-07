package vn.edu.stu.doanandroid.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "QLSVdb";
    private static final int DB_VERSION = 1;


    public Dbhelper(@Nullable Context context) {
        super(context , DB_NAME , null , DB_VERSION);
    }
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LopSql = "CREATE TABLE Lop(MaLop String primary key ," + " TenLop String)";
        String SinhVienSql = "CREATE TABLE SinhVien(MaSV String primary key , " +
                "TenSV String , " + "MaLop String, " + "HinhAnh Blob , " + "DiaChi String, "
                + "NgaySinh Date, " + "FOREIGN KEY (MaLop) references Lop(MaLop) ON DELETE CASCADE ON UPDATE CASCADE)";

        sqLiteDatabase.execSQL(LopSql);

        sqLiteDatabase.execSQL(SinhVienSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String LopSql = "DROP TABLE IF EXISTS Lop";
        String SinhVienSql = "DROP TABLE IF EXISTS SinhVien";

        sqLiteDatabase.execSQL(SinhVienSql);
        sqLiteDatabase.execSQL(LopSql);

        onCreate(sqLiteDatabase);

    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql , null);
    }

}
