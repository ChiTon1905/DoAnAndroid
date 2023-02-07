package vn.edu.stu.doanandroid.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.doanandroid.model.Lop;

public class LopDAO {
    private SQLiteDatabase db;

    public LopDAO(Context context) {
        Dbhelper helper = new Dbhelper(context);

        this.db = helper.getWritableDatabase();
    }

    public long insert(Lop emp){
        ContentValues values = new ContentValues();
        values.put("MaLop", emp.getMalop());
        values.put("TenLop", emp.getTenLop());

        return db.insert("Lop", null , values);
    }

    @SuppressLint("Range")
    public List<Lop> get(String sql, String ... selectArgs){
        List<Lop> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql , selectArgs);

        while (cursor.moveToNext()){
            Lop lop = new Lop();
            lop.setMalop(cursor.getString(cursor.getColumnIndex("MaLop")));
            lop.setTenLop(cursor.getString(cursor.getColumnIndex("TenLop")));
            list.add(lop);
        }
        return list;
    }

    public List<Lop> getAll(){
        String sql = "SELECT * FROM Lop";

        return get(sql);
    }



    public int delete (String MaLop){
        return db.delete("Lop","MaLop=?",new String[]{MaLop} );
    }

    public long deletebyLop(String id){
        return db.delete("SinhVien", "MaLop = ?", new String[]{id});
    }

    public long update (Lop lop){
        ContentValues values = new ContentValues();
        values.put("TenLop", lop.getTenLop());

        return db.update("Lop",values," MaLop = ?",new String[]{lop.getMalop()});
    }
}
