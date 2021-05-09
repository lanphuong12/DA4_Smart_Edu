package com.example.smart_edu.Admin.QlyLop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smart_edu.Admin.QlySach.QlySach_main;
import com.example.smart_edu.Admin.QlySach.Sach_adapter;
import com.example.smart_edu.Admin.QlySach.Theme.Theme_main;
import com.example.smart_edu.Model.Book;
import com.example.smart_edu.Model.Class;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QlyLop_main extends AppCompatActivity {

    ArrayList<Class> mang_class;
    Lop_adapter lop_adapter;
    ListView lv_class;
    public static Class lop = new Class();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlylop_main);

        Anhxa();
        MethodToolbar();

        GetData();
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lop = mang_class.get(position);
                Intent intent = new Intent(QlyLop_main.this, display.class);
                intent.putExtra("id_class", mang_class.get(position));
                startActivity(intent);
            }
        });

    }

    public void MethodToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addclass);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.add_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_item){
                    Add_Lophoc();
                }
                return false;
            }
        });
    }

    private void Add_Lophoc() {


    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Class>> callback = dataservice.GetDataClass();
        callback.enqueue(new Callback<List<Class>>() {

            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                ArrayList<Class> lop = (ArrayList<Class>) response.body();
                for (Class l: lop){
                    mang_class.add(l);
                }
                lop_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

                Toast.makeText(QlyLop_main.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {
        lv_class = findViewById(R.id.lv_ad_lop);
        mang_class = new ArrayList<>();
        lop_adapter = new Lop_adapter(mang_class,QlyLop_main.this);
        lv_class.setAdapter(lop_adapter);
    }
}
