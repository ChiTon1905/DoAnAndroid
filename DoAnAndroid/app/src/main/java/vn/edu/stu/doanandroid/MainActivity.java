package vn.edu.stu.doanandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import vn.edu.stu.doanandroid.dialog.ThemLopDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnQLLop).setOnClickListener(this);
        findViewById(R.id.btnQLSV).setOnClickListener(this);
        findViewById(R.id.btnThemLop).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnQLSV:
                Intent intent1 = new Intent(MainActivity.this, Activity_QuanLySV.class);
                startActivity(intent1);
                break;
            case R.id.btnQLLop:
                Intent intent = new Intent(MainActivity.this, Activity_QuanLyLop.class);
                startActivity(intent);
                break;
            case R.id.btnThemLop:
                ThemLopDialog dialog = new ThemLopDialog(this);
                dialog.show();
                break;
        }
    }


}