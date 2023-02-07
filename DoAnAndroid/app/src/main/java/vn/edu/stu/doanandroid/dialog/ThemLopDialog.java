package vn.edu.stu.doanandroid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import vn.edu.stu.doanandroid.R;
import vn.edu.stu.doanandroid.adapter.LopAdapter;
import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.sqlite.LopDAO;

public class ThemLopDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText etMaLop, etTenLop;
    public ThemLopDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_themlop);

        etMaLop = findViewById(R.id.etMaLop);
        etTenLop = findViewById(R.id.etTenLop);

        findViewById(R.id.btnLuu).setOnClickListener(this);
        findViewById(R.id.btnDong).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnLuu:
                Lop lop = new Lop();
                lop.setMalop(etMaLop.getText().toString());
                lop.setTenLop(etTenLop.getText().toString());
                LopDAO dao = new LopDAO(context);
                dao.insert(lop);
                Toast.makeText(context, "Lop da duoc luu", Toast.LENGTH_SHORT).show();

                dismiss();
                break;
            case R.id.btnDong:
                dismiss();
                break;

        }
    }
}
