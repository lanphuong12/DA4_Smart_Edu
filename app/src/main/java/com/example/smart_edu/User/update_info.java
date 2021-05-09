package com.example.smart_edu.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smart_edu.Model.Student;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_info extends AppCompatActivity {

    EditText ed_namePH, ed_sdt, ed_email, ed_address, ed_date;
    TextView tv_class, tv_nameHV, tv_hocphi;
    Button cancel, update, dateB;

    ArrayList<Student> mang_hv;

    Calendar cal;
    DatePickerDialog dpd;

    Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info_user);
        Anhxa();
        GetIntent1();

        ed_namePH.setText(student.getNamePH());
        ed_sdt.setText(student.getPhonePH());
        ed_email.setText(student.getEmail());
        ed_address.setText(student.getAddressHV());
        tv_class.setText(student.getIdClass());
        tv_nameHV.setText(student.getNameHV());
        tv_hocphi.setText(student.getTuition());
        ed_date.setText(student.getDateOfBirth().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_info.this, activity_main2.class);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfo();
            }
        });
        dateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void UpdateInfo() {
        Integer id_Hv = student.getIdHV();
        String namePH = ed_namePH.getText().toString();
        String phone = ed_sdt.getText().toString();
        String email = ed_email.getText().toString();
        Integer id_class = Integer.parseInt(tv_class.getText().toString());
        String nameHV = tv_nameHV.getText().toString();
        Integer hocphi = Integer.parseInt(tv_hocphi.getText().toString());
        String diachi = ed_address.getText().toString();

        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(ed_date.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Student hv = new Student(id_Hv, nameHV, id_class, hocphi, namePH, phone, email, diachi, date);

        Dataservice dataservice = APIService.getService();
        dataservice.UpdateHV(id_Hv, hv).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(update_info.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(update_info.this, activity_main2.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(update_info.this, "Cập nhật thông tin thất bại! \n Vui lòng thử lại sau!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Hàm hiển thị DatePicker dialog
     */
    public void showDatePickerDialog() {
        cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        dpd = new DatePickerDialog(update_info.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                ed_date.setText(mDayOfMonth + "/" + mMonth);
            }
        }, day, month, year);
        dpd.show();
    }

//    public void GetIntent() {
//        Intent intent = getIntent();
//        intent.getIntExtra("id_student", id_student);
//    }

    public void GetIntent1() {
        student = (Student) getIntent().getSerializableExtra("id_student");
    }

//    private void GetData(int id_HV){
//        Dataservice dataservice = APIService.getService();
//        Call<List<Student>> callback = dataservice.GetStudentID(id_HV);
//        callback.enqueue(new Callback<List<Student>>() {
//
//            @Override
//            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
//                ArrayList<Student> hocvien = (ArrayList<Student>) response.body();
//                for (Student hv: hocvien){
//                    mang_hv.add(hv);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Student>> call, Throwable t) {
//
//                Toast.makeText(update_info.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void Anhxa() {

        ed_namePH = findViewById(R.id.et_namePH);
        ed_sdt = findViewById(R.id.edt_sdt);
        ed_email = findViewById(R.id.edt_email);
        ed_address = findViewById(R.id.et_address);
        tv_class = findViewById(R.id.tv_class);
        tv_nameHV = findViewById(R.id.tv_nameHV);
        tv_hocphi = findViewById(R.id.tv_hocphi);
        ed_date = findViewById(R.id.ed_date_of_b);
        cancel = findViewById(R.id.bt_cancel_student);
        update = findViewById(R.id.bt_update_info_user);
        dateB = findViewById(R.id.bt_date_of_b);

        mang_hv = new ArrayList<>();
    }
}
