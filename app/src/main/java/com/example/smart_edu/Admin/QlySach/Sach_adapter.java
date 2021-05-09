package com.example.smart_edu.Admin.QlySach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smart_edu.Model.Book;
import com.example.smart_edu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Sach_adapter extends BaseAdapter {

    ArrayList<Book> arrayListbook;
    Context context;

    public Sach_adapter(ArrayList<Book> arrayListbook, Context context) {
        this.arrayListbook = arrayListbook;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListbook.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListbook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tensach ;
        ImageView imgsach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.qlysach_adapter,null);
            viewHolder.tensach = (TextView) convertView.findViewById(R.id.tv_ad_book);
            viewHolder.imgsach = (ImageView) convertView.findViewById(R.id.img_ad_book);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Book sach = (Book) getItem(position);
        viewHolder.tensach.setText(sach.getNameBook());
        Picasso.get().load(sach.getImageBook())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.errorimg)
                .into(viewHolder.imgsach);
        return convertView;
    }
}
