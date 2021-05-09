package com.example.smart_edu.Admin.QlySach.Theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Theme_adapter extends BaseAdapter {

    ArrayList<Theme> arrayListtheme;
    Context context;

    public Theme_adapter(ArrayList<Theme> arrayListtheme, Context context) {
        this.arrayListtheme = arrayListtheme;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListtheme.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListtheme.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
        TextView Name_theme ;
        TextView STT ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new Theme_adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.qlysach_theme_adapter, null);
            viewHolder.Name_theme = (TextView) convertView.findViewById(R.id.tv_theme);
            viewHolder.STT = (TextView) convertView.findViewById(R.id.tv_STT);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (Theme_adapter.ViewHolder) convertView.getTag();
        }
        Theme sach = (Theme) getItem(position);
        viewHolder.Name_theme.setText(sach.getNameTheme());
        viewHolder.STT.setText(position+1 + " - ");
        return convertView;
    }
}
