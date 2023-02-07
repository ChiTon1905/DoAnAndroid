package vn.edu.stu.doanandroid;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.stu.doanandroid.adapter.LopAdapter;
import vn.edu.stu.doanandroid.adapter.SinhVienAdapter;
import vn.edu.stu.doanandroid.helper.DateTimeHelper;
import vn.edu.stu.doanandroid.helper.ImageHelper;
import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.model.SinhVien;
import vn.edu.stu.doanandroid.sqlite.Dbhelper;
import vn.edu.stu.doanandroid.sqlite.LopDAO;
import vn.edu.stu.doanandroid.sqlite.SinhVienDAO;

public class Activity_QuanLySV extends AppCompatActivity {

    TextView tvNgaySinhDate;
    EditText etMaSV , etTenSV, etDiaChi;
    Spinner spLop;
    ImageButton btnDatePicker;
    Calendar calendar;
    Lop lop;

    Button btnLuu, btnThemAnh, btnList;

    List<Lop> lopList;
    ListView lvSinhvien;


    ImageView imgAnhSV;

    final int Request_Code_Gallery = 999;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlysv);


        addControls();
        addEvents();
    }

    private void addControls() {
        etMaSV = findViewById(R.id.etMaSV);
        etTenSV = findViewById(R.id.etTenSV);
        etDiaChi = findViewById(R.id.etDiaChi);
        spLop = findViewById(R.id.spLop);

        fillLopToSpinner();

        btnLuu = findViewById(R.id.btnLuu);
        btnThemAnh = findViewById(R.id.btnThemAnhSV);
        btnList = findViewById(R.id.btnList);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        tvNgaySinhDate = findViewById(R.id.tvNgaySinhDate);
        calendar = Calendar.getInstance();

        lvSinhvien = findViewById(R.id.lvSinhVien);

        imgAnhSV = findViewById(R.id.imgAnhSV);

    }
    private void fillLopToSpinner(){
        LopDAO dao = new LopDAO(this);
        lopList = dao.getAll();
        LopAdapter lopAdapter = new LopAdapter(this, lopList);
        spLop.setAdapter(lopAdapter);
    }

    private void addEvents() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyNgay();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinhVienDAO dao = new SinhVienDAO(Activity_QuanLySV.this);
                try {
                    int index =spLop.getSelectedItemPosition();
                    if (index >= -1) {
                        lop = lopList.get(index);
                    }
                    String msg;
                    java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
                    dao.insertData(
                            etMaSV.getText().toString().trim(),
                            etTenSV.getText().toString().trim(),
                            lop.getMalop(),
                            etDiaChi.getText().toString().trim(),
                            sqlDate,
                            imageViewToByte(imgAnhSV)
                    );
                    msg = "Sinh vien da duoc luu";
                    Snackbar snackbar = Snackbar.make(
                            view,
                            msg,
                            Snackbar.LENGTH_LONG
                    );
                    snackbar.show();
                    etMaSV.setText("");
                    etTenSV.setText("");
                    etDiaChi.setText("");
                    tvNgaySinhDate.setText("dd-MM-yyyy");
                    imgAnhSV.setImageResource(R.mipmap.ic_launcher);
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(Activity_QuanLySV.this,"Loi :"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Activity_QuanLySV.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Request_Code_Gallery

                );

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_QuanLySV.this,Activity_DanhSachSV.class);
                startActivity(intent);
            }
        });
    }

    private void xulyNgay() {
        DatePickerDialog.OnDateSetListener listener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DATE, i2);
                        tvNgaySinhDate.setText(DateTimeHelper.toString(calendar.getTime()));

                    }
                };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Activity_QuanLySV.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == Request_Code_Gallery){
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Request_Code_Gallery);
                }
                else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Ban khong co quyen truy cap",
                            Toast.LENGTH_LONG
                    ).show();
                }
                return;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == Request_Code_Gallery && resultCode == RESULT_OK && data != null ){
           Uri uri = data.getData();
           try {
               InputStream inputStream = getContentResolver().openInputStream(uri);

               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               imgAnhSV.setImageBitmap(bitmap);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
       }
    }
}
