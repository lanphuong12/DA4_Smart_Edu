package com.example.smart_edu.Admin.QlySach;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smart_edu.Admin.QlySach.Theme.Theme_main;
import com.example.smart_edu.Model.Book;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QlySach_main extends AppCompatActivity {

    ArrayList<Book> mang_book;
    Sach_adapter sach_adapter;
    ListView lv_ad_book;
    public static Book sach = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlysach_main);

        Anhxa();
        MethodToolbar();

        GetData();


        lv_ad_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sach = mang_book.get(position);
                Intent intent = new Intent(QlySach_main.this, Theme_main.class);
                intent.putExtra("id_book", mang_book.get(position));
                startActivity(intent);
            }
        });

    }
    public void MethodToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addbook);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.add_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_item){
                    Add_book();
                }
                return false;
            }
        });
    }

    private void Add_book() {
        Dialog dialog = new Dialog(QlySach_main.this);
        dialog.setContentView(R.layout.qlysach_add);
        dialog.show();

        EditText et_name_book = dialog.findViewById(R.id.et_name_book);
        EditText ed_img_book = dialog.findViewById(R.id.ed_img_book);

        Button btnAdd = dialog.findViewById(R.id.bt_add);
        btnAdd.setText("THÊM MỚI");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_book = et_name_book.getText().toString();
                String img_book = ed_img_book.getText().toString();

                Dataservice dataservice = APIService.getService();
                dataservice.addBook(name_book,img_book).enqueue(new Callback<Book>() {
                    @Override
                    public void onResponse(Call<Book> call, Response<Book> response) {
                        Toast.makeText(QlySach_main.this,"Thêm sách mới thành công",Toast.LENGTH_SHORT).show();
                        mang_book.clear();
                        dialog.cancel();
                        GetData();
                        sach_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Book> call, Throwable t) {
                        Toast.makeText(QlySach_main.this, "Thêm sách mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void GetData(){
        Dataservice dataservice = APIService.getService();
        Call<List<Book>> callback = dataservice.GetDataBook();
        callback.enqueue(new Callback<List<Book>>() {

            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                ArrayList<Book> book = (ArrayList<Book>) response.body();
                //Log.d("api",response.toString());
                for (Book b: book){
                    mang_book.add(b);
                }
                sach_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

                Toast.makeText(QlySach_main.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {
        lv_ad_book = findViewById(R.id.lv_ad_book);
        mang_book = new ArrayList<>();
        sach_adapter = new Sach_adapter(mang_book,QlySach_main.this);
        lv_ad_book.setAdapter(sach_adapter);
    }
}
