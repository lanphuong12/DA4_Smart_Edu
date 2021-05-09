package com.example.smart_edu.Admin.QlyLop;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Admin.QlyHocvien.HV_adapter;
import com.example.smart_edu.Model.Class;
import com.example.smart_edu.Model.Student;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class display extends AppCompatActivity {

    TextView name_class, id_book, date, hour;
    Class lop;
    ArrayList<Student> mang_hv;
    HV_adapter hv_adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlylop_display);

        Anhxa();
        MethodToolbar();

        GetData_intent();

        name_class.setText(lop.getNameClass());
        id_book.setText(lop.getIdBook().toString());
        date.setText(lop.getDateStudy());
        hour.setText(lop.getHourStudy());

        GetData(lop.getIdClass());

        registerForContextMenu(recyclerView);
    }
    public void MethodToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addHV);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.add_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_item){
                    Add_Hocvien();
                }
                return false;
            }
        });
    }

    private void Add_Hocvien() {

    }

    private void GetData_intent() {
        lop = (Class) getIntent().getSerializableExtra("id_class");
    }

    private void GetData(int id_class){
        Dataservice dataservice = APIService.getService();
        Call<List<Student>> callback = dataservice.GetStudent(id_class);
        callback.enqueue(new Callback<List<Student>>() {

            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                mang_hv = (ArrayList<Student>) response.body();
                hv_adapter = new HV_adapter(getApplicationContext(),mang_hv);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(hv_adapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

                Toast.makeText(display.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {
        name_class = findViewById(R.id.tv_name_class);
        id_book = findViewById(R.id.tv_book);
        date = findViewById(R.id.tv_date_study);
        hour = findViewById(R.id.tv_hour_study);
        recyclerView = findViewById(R.id.lv_ad_listhv);
        mang_hv = new ArrayList<>();
        hv_adapter = new HV_adapter(display.this, mang_hv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vocab, menu);
    }
}
