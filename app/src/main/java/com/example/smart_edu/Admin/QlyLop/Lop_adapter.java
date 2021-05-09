package com.example.smart_edu.Admin.QlyLop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


import com.example.smart_edu.Model.Class;
import com.example.smart_edu.R;

import java.util.ArrayList;

public class Lop_adapter extends BaseAdapter {

    ArrayList<Class> arrayListclass;
    Context context;

    public Lop_adapter(ArrayList<Class> arrayListclass, Context context) {
        this.arrayListclass = arrayListclass;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListclass.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListclass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tenlop, ngayhoc, giohoc ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new Lop_adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.qlylop_adapter,null);
            viewHolder.tenlop = (TextView) convertView.findViewById(R.id.tv_name_lop);
            viewHolder.ngayhoc = (TextView) convertView.findViewById(R.id.tv_admin_ngayhoc);
            viewHolder.giohoc = (TextView) convertView.findViewById(R.id.tv_admin_giohoc);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (Lop_adapter.ViewHolder) convertView.getTag();
        }
        Class lop = (Class) getItem(position);
        viewHolder.tenlop.setText(lop.getNameClass());
        viewHolder.ngayhoc.setText(lop.getDateStudy());
        viewHolder.giohoc.setText(lop.getHourStudy());
        return convertView;
    }

}
