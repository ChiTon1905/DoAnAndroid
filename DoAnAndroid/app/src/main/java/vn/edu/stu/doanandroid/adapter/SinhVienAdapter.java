package vn.edu.stu.doanandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.stu.doanandroid.R;
import vn.edu.stu.doanandroid.helper.DateTimeHelper;
import vn.edu.stu.doanandroid.model.SinhVien;

public class SinhVienAdapter extends BaseAdapter {
    private Context context;
    private List<SinhVien> list;
    private int layout;

    public SinhVienAdapter(Context context, List<SinhVien> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView tvMaSV,tvTenSV,tvMaLop,tvDiaChi,tvNgaySinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.tvMaSV = row.findViewById(R.id.tvMaSVitem);
            holder.tvTenSV = row.findViewById(R.id.tvTenSVitem);
            holder.tvMaLop = row.findViewById(R.id.tvMaLopitem);
            holder.tvDiaChi = row.findViewById(R.id.tvDiaChiitem);
            holder.tvNgaySinh = row.findViewById(R.id.tvNgaySinhitem);
            holder.imageView = row.findViewById((R.id.imgAnhSVitem));
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        SinhVien sinhVien = list.get(i);

        holder.tvMaSV.setText(sinhVien.getMaSV());
        holder.tvTenSV.setText(sinhVien.getTenSV());
        holder.tvMaLop.setText(sinhVien.getMaLop());
        holder.tvDiaChi.setText(sinhVien.getDiaChi());

        holder.tvNgaySinh.setText(DateTimeHelper.toString(sinhVien.getNgaySinh()));


        byte[] SVImage = sinhVien.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(SVImage, 0 , SVImage.length);
        holder.imageView.setImageBitmap(bitmap);


        return row;
    }
}
