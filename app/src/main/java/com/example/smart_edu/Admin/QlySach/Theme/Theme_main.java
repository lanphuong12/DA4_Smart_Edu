package com.example.smart_edu.Admin.QlySach.Theme;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smart_edu.Model.Book;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Theme_main extends AppCompatActivity {

    ArrayList<Theme> mang_theme;
    Theme_adapter theme_adapter;
    public static Theme theme = new Theme();
    ListView lv_theme;
    Book book;

    TextView tv_namebook, tv_idbook, tv_url_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlysach_theme_main);
        Anhxa();
        MethodToolbar();
        GetData_intent();

        tv_namebook.setText(book.getNameBook());
        tv_idbook.setText(book.getIdBook().toString());
        tv_url_img.setText(book.getImageBook());

        GetData(book.getIdBook());

        lv_theme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                theme = mang_theme.get(position);
                Intent intent = new Intent(Theme_main.this, Theme_display.class);
                intent.putExtra("id_theme", mang_theme.get(position));
                startActivity(intent);
            }
        });
    }

    public void MethodToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addtheme);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Smart Edu");
        toolbar.inflateMenu(R.menu.add_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_item){
                    DialogThemTheme();
                }
                return false;
            }
        });
    }

    private void GetData_intent() {
        book = (Book) getIntent().getSerializableExtra("id_book");
    }


    private void DialogThemTheme() {
        Dialog dialog = new Dialog(Theme_main.this);
        dialog.setContentView(R.layout.qlysach_theme_add);
        dialog.show();

        EditText ed_id_book = dialog.findViewById(R.id.ed_ma_sach);
        EditText edt_name_theme = dialog.findViewById(R.id.et_name_theme);
        EditText edt_id_lession = dialog.findViewById(R.id.ed_id_lession);

        ed_id_book.setText(book.getIdBook());

        Button btnAdd = dialog.findViewById(R.id.bt_add_theme);
        Button btnCancel = dialog.findViewById(R.id.bt_cancel_theme);
        btnCancel.setText("HUỶ");
        btnAdd.setText("THÊM MỚI");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_book = Integer.parseInt(ed_id_book.getText().toString());
                String name_theme = edt_name_theme.getText().toString();
                String id_lession = edt_id_lession.getText().toString();

                Dataservice dataservice = APIService.getService();
                dataservice.addTheme(id_book,name_theme,id_lession).enqueue(new Callback<Theme>() {
                    @Override
                    public void onResponse(Call<Theme> call, Response<Theme> response) {
                        Toast.makeText(Theme_main.this,"Thêm từ mới thành công",Toast.LENGTH_SHORT).show();
                        mang_theme.clear();
                        dialog.cancel();
                        GetData(id_book);
                        theme_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Theme> call, Throwable t) {
                        Toast.makeText(Theme_main.this, "Thêm từ mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    private void GetData(int id_book){
        Dataservice dataservice = APIService.getService();
        Call<List<Theme>> callback = dataservice.GetTheme_book(id_book);
        callback.enqueue(new Callback<List<Theme>>() {

            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                ArrayList<Theme> theme = (ArrayList<Theme>) response.body();
                for (Theme the: theme){
                    mang_theme.add(the);
                }
                theme_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

                Toast.makeText(Theme_main.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {

        tv_namebook = findViewById(R.id.tv_name_book);
        tv_idbook = findViewById(R.id.tv_id_book);
        tv_url_img = findViewById(R.id.tv_image_book);

        lv_theme = findViewById(R.id.lv_ad_book_theme);
        mang_theme = new ArrayList<>();
        theme_adapter = new Theme_adapter(mang_theme,Theme_main.this);
        lv_theme.setAdapter(theme_adapter);
        registerForContextMenu(lv_theme);  // Đăng ký Contexmenu cho Re
    }
}
