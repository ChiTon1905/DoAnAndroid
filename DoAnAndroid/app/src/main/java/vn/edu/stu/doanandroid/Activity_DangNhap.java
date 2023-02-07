package vn.edu.stu.doanandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_DangNhap extends AppCompatActivity {

    EditText etTaiKhoan, etMatKhau;
    Button btnDangNhap, btnDangKy , btnThoat;
    String tk,mk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        addControls();
        addEvents();
    }



    private void addControls() {
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnThoat = findViewById(R.id.btnThoat);
    }

    private void addEvents() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DangNhap.this , android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Ban co muon thoat ?");
                builder.setMessage("Hay lua chon !");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Activity_DangNhap.this);
                dialog.setTitle("Hop thoai xu ly");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_dangky);
                EditText ettk = dialog.findViewById(R.id.etTaiKhoan);
                EditText etmk = dialog.findViewById(R.id.etMatKhau);
                Button btnHuy = dialog.findViewById(R.id.btnHuy);
                Button btnDongY = dialog.findViewById(R.id.btnDongY);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tk = ettk.getText().toString().trim();
                        mk = etmk.getText().toString().trim();

                        etTaiKhoan.setText(tk);
                        etMatKhau.setText(mk);

                        dialog.cancel();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTaiKhoan.getText().length() != 0 && etMatKhau.getText().length() != 0){
                    if(etTaiKhoan.getText().toString().equals(tk) && etMatKhau.getText().toString().equals(mk)){
                        Toast.makeText(
                                Activity_DangNhap.this,
                                "Ban da dang nhap thanh cong",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(Activity_DangNhap.this, MainActivity.class);
                        startActivity(intent);
                    }else  if(etTaiKhoan.getText().toString().equals("root") && etMatKhau.getText().toString().equals("root")){
                        Toast.makeText(
                                Activity_DangNhap.this,
                                "Ban da dang nhap thanh cong",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(Activity_DangNhap.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(
                                Activity_DangNhap.this,
                                "Ban da dang nhap that bai",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }else {
                    Toast.makeText(
                            Activity_DangNhap.this,
                            "Ban nen dien thong tin vao",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });
    }
}