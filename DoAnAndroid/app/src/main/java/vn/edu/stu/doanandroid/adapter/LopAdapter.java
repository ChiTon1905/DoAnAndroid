package vn.edu.stu.doanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.edu.stu.doanandroid.R;
import vn.edu.stu.doanandroid.model.Lop;

public class LopAdapter extends BaseAdapter {
    private Context context;
    private List<Lop> list;

    public LopAdapter(Context context, List<Lop> list) {
        this.context = context;
        this.list = list;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_lop_item,null);
        }

        TextView tvMaLop = view.findViewById(R.id.tvMaLopitem);
        TextView tvTenLop = view.findViewById(R.id.tvTenLopitem);

        Lop l = list.get(i);
        tvMaLop.setText(l.getMalop());
        tvTenLop.setText(l.getTenLop());
        return view;
    }
}
