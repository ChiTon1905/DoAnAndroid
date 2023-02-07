package vn.edu.stu.doanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.sqlite.LopDAO;

public class Activity_ThemSuaLop extends AppCompatActivity {
    EditText etMaLop , etTenLop;
    Button btnLuu , btnDong;
    Lop lop;
    int resultCode = 115;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_themlop);

        addControls();
        addEvents();
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();

        if (intent.hasExtra("CHON")){
            lop = (Lop) intent.getSerializableExtra("CHON");
            if (lop != null){
                etMaLop.setText(lop.getMalop());
                etTenLop.setText(lop.getTenLop());
            }else {
                etMaLop.setText("");
                etTenLop.setText("");
            }
        }
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lop == null){
                    lop = new Lop();
                }
                lop.setMalop(etMaLop.getText().toString());
                lop.setTenLop(etTenLop.getText().toString());
                LopDAO dao = new LopDAO(Activity_ThemSuaLop.this);
                dao.update(lop);
                Intent intent = getIntent();
                intent.putExtra("TRA",lop);
                setResult(resultCode,intent);
                finish();
            }
        });
    }

    private void addControls() {
        etMaLop = findViewById(R.id.etMaLop);
        etTenLop = findViewById(R.id.etTenLop);
        btnLuu = findViewById(R.id.btnLuu);
        btnDong = findViewById(R.id.btnDong);
    }
}
