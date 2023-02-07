package vn.edu.stu.doanandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import vn.edu.stu.doanandroid.adapter.LopAdapter;
import vn.edu.stu.doanandroid.dialog.ThemLopDialog;
import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.sqlite.LopDAO;
import vn.edu.stu.doanandroid.sqlite.SinhVienDAO;

public class Activity_QuanLyLop extends AppCompatActivity {

    Lop lop;

    ListView lvLop;
    List<Lop> list;
    LopAdapter lopAdapter;
    EditText etMaLopSua, etTenLopSua;
    Button btnSua;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlylop);

        addControls();
        fillClassesListView();
        addEvents();
    }


    void fillClassesListView() {
        LopDAO dao = new LopDAO(this);
        list = dao.getAll();

        lopAdapter = new LopAdapter(this, list);
        lvLop.setAdapter(lopAdapter);
    }

    private void addControls() {
        lvLop = findViewById(R.id.lvLop);

    }
    private void addEvents() {
        lvLop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                LopDAO dao = new LopDAO(Activity_QuanLyLop.this);
                Lop lop = list.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_QuanLyLop.this);
                builder.setTitle("Bạn chắc chắn muốn xóa");
                builder.setMessage("Hãy lựa chọn:");
                builder.setIcon(R.drawable.alert_icon);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.delete("" + lop.getMalop());
                        dao.deletebyLop(""+lop.getMalop());
                        lopAdapter.notifyDataSetChanged();

                        fillClassesListView();
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.show();
                return true;
            }
        });
        lvLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lop = list.get(i);
                showSuaDialog(Activity_QuanLyLop.this,lop.getMalop());

            }
        });
    }

    private void showSuaDialog(Activity activity,String MaLop) {

        LopDAO dao = new LopDAO(activity);

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_sua_lop);
        dialog.setTitle("SuaLop");

        etMaLopSua = dialog.findViewById(R.id.etMaLopSua);
        etTenLopSua = dialog.findViewById(R.id.etTenLopSua);
        btnSua = dialog.findViewById(R.id.btnSua);

        etMaLopSua.setText(lop.getMalop());
        etTenLopSua.setText(lop.getTenLop());

        //set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        //set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lop lop = new Lop();

                lop.setMalop(MaLop);
                lop.setTenLop(etTenLopSua.getText().toString().trim());

                dao.update(lop);
                lopAdapter.notifyDataSetChanged();

                fillClassesListView();
                dialog.dismiss();
            }
        });

    }



}

