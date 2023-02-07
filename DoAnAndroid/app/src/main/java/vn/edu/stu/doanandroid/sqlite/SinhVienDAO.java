package vn.edu.stu.doanandroid.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.doanandroid.helper.DateTimeHelper;
import vn.edu.stu.doanandroid.helper.ImageHelper;
import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.model.SinhVien;

public class SinhVienDAO {

    private SQLiteDatabase db;

    public SinhVienDAO(Context context) {
        Dbhelper helper = new Dbhelper(context);

        this.db = helper.getWritableDatabase();
    }

    public  void insertData(String MaSV,String TenSV,String MaLop,String DiaChi,Date NgaySinh,byte[] HinhAnh){

        String sql = "INSERT INTO SinhVien VALUES (?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1,MaSV);
        statement.bindString(2,TenSV);
        statement.bindString(3,MaLop);
        statement.bindBlob(4,HinhAnh);
        statement.bindString(5,DiaChi);
        statement.bindString(6,DateTimeHelper.toString(NgaySinh));

        statement.executeInsert();
    }

    public long update(SinhVien sinhVien){
        ContentValues values = new ContentValues();

        values.put("TenSV", sinhVien.getTenSV());
        values.put("MaLop", sinhVien.getMaLop());
        values.put("HinhAnh", sinhVien.getHinhAnh());
        values.put("DiaChi", sinhVien.getDiaChi());
        values.put("NgaySinh", DateTimeHelper.toString(sinhVien.getNgaySinh()));

        return db.update("SinhVien",values,"MaSV = ?",new String[]{sinhVien.getMaSV()});
    }

    public long delete(String id){
        return db.delete("SinhVien", "MaSV = ?", new String[]{id});
    }



    @SuppressLint("Range")
    public List<SinhVien> get(String sql, String ... selectArgs) throws ParseException {
        List<SinhVien> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql , selectArgs);

        while (cursor.moveToNext()){
            SinhVien sinhVien = new SinhVien();
            sinhVien.setMaSV(cursor.getString(cursor.getColumnIndex("MaSV")));
            sinhVien.setTenSV(cursor.getString(cursor.getColumnIndex("TenSV")));
            sinhVien.setMaLop(cursor.getString(cursor.getColumnIndex("MaLop")));
            sinhVien.setDiaChi(cursor.getString(cursor.getColumnIndex("DiaChi")));

            byte[] HinhAnh = cursor.getBlob(cursor.getColumnIndex("HinhAnh"));
            sinhVien.setHinhAnh(HinhAnh);

            java.sql.Date sqlDate = new Date(DateTimeHelper.toDate(cursor.getString(cursor.getColumnIndex("NgaySinh"))).getTime());
            sinhVien.setNgaySinh(sqlDate);

            list.add(sinhVien);
        }
        return list;
    }

    public List<SinhVien> getAll() throws ParseException {
        String sql = "SELECT * FROM SinhVien";

        return get(sql);
    }

    public List<SinhVien> getMaSV() throws ParseException{
        String sql = "Select MaSV from SinhVien";

        return get(sql);
    }



}
