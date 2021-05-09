package com.example.smart_edu.Admin.QlyHocvien;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Model.Student;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;
import com.example.smart_edu.User.info_user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QlyHV_main extends AppCompatActivity {

    EditText edt_search;
    ImageButton imgbt_search;
    RecyclerView recyclerView;
    ArrayList<Student> mang_hv;
    HV_adapter hv_adapter;

    EditText nameHV, tuition, dateb, namePH, email, phone, address;
    Spinner sp_idclass;
    ImageButton imgcalendar;
    Button cancel, add;
    Calendar cal;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qlyhv_main);
        MethodToolbar();
        Anhxa();
        GetDataStudent();
    }

    private void GetDataStudent() {
        Dataservice dataservice = APIService.getService();
        Call<List<Student>> callback = dataservice.GetDataStudent();
        callback.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                mang_hv = (ArrayList) response.body();
                hv_adapter = new HV_adapter(getApplicationContext(), mang_hv);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(hv_adapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

            }
        });
    }

    public void MethodToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_hvmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Smart Edu");
        toolbar.setSubtitle("Quản lý học viên");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    DialogThemHV();
                }
                return false;
            }
        });
    }

    private void DialogThemHV() {
        Dialog dialog = new Dialog(QlyHV_main.this);
        dialog.setContentView(R.layout.qlyhv_add);
        dialog.show();
        AnhxaDialog();
        imgcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        String tenhv = nameHV.getText().toString();
        int malop = Integer.parseInt(sp_idclass.toString());
        int hocphi = Integer.parseInt(tuition.getText().toString());

        Date ngay1 = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            ngay1 = format.parse(dateb.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tenPH = namePH.getText().toString();
        String mail = email.getText().toString();
        String sdt = phone.getText().toString();
        String diachi = address.getText().toString();

        cancel.setText("HUỶ");
        add.setText("THÊM");
        Date finalNgay = ngay1;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dataservice dataservice = APIService.getService();
                dataservice.addHV(tenhv, malop, hocphi, tenPH, sdt, mail, diachi, finalNgay)
                        .enqueue(new Callback<Student>() {
                            @Override
                            public void onResponse(Call<Student> call, Response<Student> response) {
                                Toast.makeText(QlyHV_main.this, "ADD successful!!!", Toast.LENGTH_SHORT).show();
                                mang_hv.clear();
                                dialog.cancel();
                                GetDataStudent();
                                hv_adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<Student> call, Throwable t) {
                                Toast.makeText(QlyHV_main.this, "ADD UNsuccessful!!!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.dismiss();

    }
    private void showDatePickerDialog() {
        cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        dpd = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                dateb.setText(mDayOfMonth +"/"+ mMonth );
            }
        }, day, month, year);
        dpd.show();
    }
    private void AnhxaDialog() {
        nameHV = findViewById(R.id.et_nameHV);
        nameHV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sp_idclass = findViewById(R.id.sp_class);
        tuition = findViewById(R.id.et_tuition);
        dateb = findViewById(R.id.et_date_of_b);
        imgcalendar = findViewById(R.id.bt_date_of_b);
        namePH = findViewById(R.id.et_namePH);
        email = findViewById(R.id.edt_email);
        phone = findViewById(R.id.edt_sdt);
        address = findViewById(R.id.et_address);
        cancel = findViewById(R.id.bt_cancel_hv);
        add = findViewById(R.id.bt_addHV);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    private void Anhxa() {
        edt_search = findViewById(R.id.edt_search_hv);
        imgbt_search = findViewById(R.id.imgbt_search_hv);
        recyclerView = findViewById(R.id.lv_ad_hocvien);

        mang_hv = new ArrayList<>();
        hv_adapter = new HV_adapter(QlyHV_main.this, mang_hv);
    }


    public void Timkiem(View view) {
        edt_search.getText().toString();
        Dataservice dataservice = APIService.getService();
//        dataservice.SearchHV()
    }
}
