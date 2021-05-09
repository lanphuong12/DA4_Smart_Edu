package com.example.smart_edu.Admin.QlySach.Theme;

import android.app.Dialog;
import android.app.backup.BackupAgent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Admin.QlySach.Theme.Vocabulary.Vocab_adapter;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.Model.Vocab;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Theme_display extends AppCompatActivity {

    TextView tv_theme, tv_url, tv_id_theme;
    Theme theme;
    ArrayList<Vocab> mang_vocab;
    Vocab_adapter vocab_adapter;
    RecyclerView recyclerView;
    int mCurrentItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlysach_theme_display);
        Anhxa();
        MethodToolbar();

        GetData_intent();

        tv_theme.setText(theme.getNameTheme());
        tv_url.setText(theme.getIdLinkLesson());
        tv_id_theme.setText(theme.getIdTheme().toString());


        GetData(theme.getIdTheme());

        registerForContextMenu(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
    public void MethodToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_addvocab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Smart Edu");
        toolbar.setSubtitle("Vocab");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.add_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.add_item){
                    DialogThemVocab();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    private void GetData_intent() {
        theme = (Theme) getIntent().getSerializableExtra("id_theme");
    }

    private void GetData(int id_theme) {
        Dataservice dataservice = APIService.getService();
        Call<List<Vocab>> callback = dataservice.GetVocabulary(id_theme);
        callback.enqueue(new Callback<List<Vocab>>() {

            @Override
            public void onResponse(Call<List<Vocab>> call, Response<List<Vocab>> response) {
                mang_vocab = (ArrayList<Vocab>) response.body();
                vocab_adapter = new Vocab_adapter(getApplicationContext(), mang_vocab);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(vocab_adapter);
            }

            @Override
            public void onFailure(Call<List<Vocab>> call, Throwable t) {

                Toast.makeText(Theme_display.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Anhxa() {
        tv_theme = findViewById(R.id.tv_theme);
        tv_url = findViewById(R.id.tv_url_theme);
        tv_id_theme = findViewById(R.id.tv_id_theme);
        recyclerView = findViewById(R.id.re_listvocab);
        mang_vocab = new ArrayList<>();
        vocab_adapter = new Vocab_adapter(Theme_display.this, mang_vocab);

    }

    private void Delete(int id_vocab) {
        Dataservice dataservice = APIService.getService();
        Call<Void> callback = dataservice.DeleteVocab(id_vocab);
        callback.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Theme_display.this, "Delete succesful!!!", Toast.LENGTH_SHORT).show();
                mang_vocab.clear();
                GetData(theme.getIdTheme());
                vocab_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(Theme_display.this, "Delete unsuccesful!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogThemVocab(){
        Dialog dialog = new Dialog(Theme_display.this);
        dialog.setContentView(R.layout.qlysach_theme_vocab_add);
        dialog.show();

        TextView tv_id_theme = dialog.findViewById(R.id.tv_id_theme);
        EditText edt_word = dialog.findViewById(R.id.ed_new_word);
        EditText edt_meaning = dialog.findViewById(R.id.ed_meaning);

        tv_id_theme.setText(theme.getIdTheme().toString());
        Button btnAdd = dialog.findViewById(R.id.bt_method_vocab);
        Button btCancel = dialog.findViewById(R.id.bt_cancel_vocab);
        btCancel.setText("HUỶ");
        btnAdd.setText("THÊM MỚI");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_theme = Integer.parseInt(tv_id_theme.getText().toString());
                String word = edt_word.getText().toString();
                String meaning = edt_meaning.getText().toString();

                Dataservice dataservice = APIService.getService();
                dataservice.addVocab(id_theme,word,meaning).enqueue(new Callback<Vocab>() {
                    @Override
                    public void onResponse(Call<Vocab> call, Response<Vocab> response) {
                        Toast.makeText(Theme_display.this,"Thêm từ mới thành công",Toast.LENGTH_SHORT).show();
                        mang_vocab.clear();
                        dialog.cancel();
                        GetData(id_theme);
                        vocab_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Vocab> call, Throwable t) {
                        Toast.makeText(Theme_display.this, "Thêm từ mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        dialog.dismiss();
    }

    private void UpdateVocab(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.qlysach_theme_vocab_add);
        dialog.show();

        TextView tv_id_theme = dialog.findViewById(R.id.tv_id_theme);
        EditText edt_word = dialog.findViewById(R.id.ed_new_word);
        EditText edt_meaning = dialog.findViewById(R.id.ed_meaning);

        Vocab tumoi = mang_vocab.get(mCurrentItemPosition);
        tv_id_theme.setText(tumoi.getIdTheme().toString());
        edt_word.setText(tumoi.getWord());
        edt_meaning.setText(tumoi.getMeaning());

        Button btnSua = dialog.findViewById(R.id.bt_method_vocab);
        Button btn_Cancel = dialog.findViewById(R.id.bt_cancel_vocab);
        btn_Cancel.setText("HUỶ");
        btnSua.setText("CẬP NHẬT");
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_vocab = tumoi.getIdVocab();
                int id_theme = Integer.parseInt(tv_id_theme.getText().toString());
                String word = edt_word.getText().toString();
                String meaning = edt_meaning.getText().toString();

                Vocab newWord = new Vocab(id_vocab, id_theme, word, meaning);

                Dataservice dataservice = APIService.getService();
                Call<Void> callback = dataservice.UpdateVocab(id_vocab, newWord);
                callback.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Theme_display.this, "Update Vocab succesful!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        mang_vocab.clear();
                        GetData(theme.getIdTheme());
                        vocab_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(Theme_display.this, "Update vocab unsuccesful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        mCurrentItemPosition = vocab_adapter.getPosition();
        switch (item.getItemId()){
            case R.id.delete:
                Delete(mang_vocab.get(mCurrentItemPosition).getIdVocab());
                break;
            case R.id.update:
                UpdateVocab();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
