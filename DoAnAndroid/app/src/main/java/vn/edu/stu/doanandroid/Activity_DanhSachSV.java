package vn.edu.stu.doanandroid;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.stu.doanandroid.adapter.LopAdapter;
import vn.edu.stu.doanandroid.adapter.SinhVienAdapter;
import vn.edu.stu.doanandroid.helper.DateTimeHelper;
import vn.edu.stu.doanandroid.model.Lop;
import vn.edu.stu.doanandroid.model.SinhVien;
import vn.edu.stu.doanandroid.sqlite.LopDAO;
import vn.edu.stu.doanandroid.sqlite.SinhVienDAO;

public class Activity_DanhSachSV extends AppCompatActivity {

    ListView listView;
    List<SinhVien> list;
    SinhVienAdapter adapter ;

    Spinner splopdialog;

    TextView tvNgaySinhDatedialog;
    EditText etMaSVdialog , etTenSVdialog, etDiaChidialog;
    ImageButton btnDatePickerdialog;
    ImageView imgAnhSVdialog;
    Button btnCapNhat;
    Calendar calendar;
    Lop lop;
    SinhVien sinhVien;

    List<Lop> lopList;
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachsv);



        listView = findViewById(R.id.lvDanhSachSV);
        list = new ArrayList<>();
        adapter = new SinhVienAdapter(this, list, R.layout.sinhvien_item);
        listView.setAdapter(adapter);

        addEvents();
        fillSinhVienListview();


    }

    private void fillSinhVienListview() {
        SinhVienDAO dao = new SinhVienDAO(this);
        try {
            list = dao.getAll();

            adapter = new SinhVienAdapter(this,list,R.layout.sinhvien_item);
            listView.setAdapter(adapter);
        } catch (ParseException ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Loi :" + ex.getMessage()
                    , Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void addEvents() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                position = i;
                sinhVien = list.get(i);

                CharSequence[] items = {"Cập nhật","Xóa"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_DanhSachSV.this);

                dialog.setTitle("Chọn chức năng");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            //update
                            showDialogUpdate(Activity_DanhSachSV.this,sinhVien.getMaSV());
                            Toast.makeText(getApplicationContext(),"Đang cập nhật",Toast.LENGTH_LONG).show();
                        }else{
                            SinhVienDAO dao = new SinhVienDAO(Activity_DanhSachSV.this);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSachSV.this);
                            builder.setTitle("Chú ý");
                            builder.setMessage("Hãy lựa chọn:");
                            builder.setIcon(R.drawable.alert_icon);
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dao.delete("" + sinhVien.getMaSV());
                                    Toast.makeText(getApplicationContext(),"Đã xóa",Toast.LENGTH_LONG).show();
                                    adapter.notifyDataSetChanged();

                                    fillSinhVienListview();
                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onBackPressed();
                                }
                            });
                            builder.show();

                        }
                    }
                });
                dialog.show();
                adapter.notifyDataSetChanged();

                fillSinhVienListview();
                return true;
            }
        });
    }
    private void showDialogUpdate(Activity activity,final String MaSV){
        SinhVienDAO dao = new SinhVienDAO(activity);

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_sua_sinhvien);
        dialog.setTitle("Cập nhật");

        imgAnhSVdialog = dialog.findViewById(R.id.imgAnhSVdialog);
        etMaSVdialog = dialog.findViewById(R.id.etMaSVdialog);
        etTenSVdialog = dialog.findViewById(R.id.etTenSVdialog);
        etDiaChidialog = dialog.findViewById(R.id.etDiaChidialog);
        tvNgaySinhDatedialog = dialog.findViewById(R.id.tvNgaySinhDatedialog);

        btnDatePickerdialog = dialog.findViewById(R.id.btnDatePickerdialog);
        btnCapNhat = dialog.findViewById(R.id.btnCapNhatdialog);
        calendar = Calendar.getInstance();

        splopdialog = dialog.findViewById(R.id.spLopdialog);

        etMaSVdialog.setText(sinhVien.getMaSV());
        etTenSVdialog.setText(sinhVien.getTenSV());
        etDiaChidialog.setText(sinhVien.getDiaChi());
        tvNgaySinhDatedialog.setText(DateTimeHelper.toString(sinhVien.getNgaySinh()));
        splopdialog.setSelection(position);

        fillLopToSpinner();
        addEventsdialog();

        //set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        //set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SinhVien sinhVien = new SinhVien();
                    sinhVien.setMaSV(MaSV);
                    sinhVien.setTenSV(etTenSVdialog.getText().toString().trim());
                    sinhVien.setDiaChi(etDiaChidialog.getText().toString().trim());

                    java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
                    sinhVien.setNgaySinh(sqlDate);

                    lop = (Lop) splopdialog.getSelectedItem();
                    sinhVien.setMaLop(lop.getMalop());

                    sinhVien.setHinhAnh(imageViewToByte(imgAnhSVdialog));
                    dao.update(sinhVien);
                    adapter.notifyDataSetChanged();

                    fillSinhVienListview();
                    dialog.dismiss();
            }
        });


    }

    private void addEventsdialog() {
        btnDatePickerdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyNgay();
            }
        });

        imgAnhSVdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Activity_DanhSachSV.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
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
                        tvNgaySinhDatedialog.setText(DateTimeHelper.toString(calendar.getTime()));

                    }
                };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Activity_DanhSachSV.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }


    private void fillLopToSpinner() {
        LopDAO dao = new LopDAO(this);
        lopList = dao.getAll();
        LopAdapter lopAdapter = new LopAdapter(this, lopList);
        splopdialog.setAdapter(lopAdapter);
    }
    public static byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 888){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
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
        if (requestCode == 888 && resultCode == RESULT_OK && data != null ){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhSVdialog.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}


