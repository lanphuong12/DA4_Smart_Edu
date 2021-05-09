package com.example.smart_edu.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smart_edu.MainActivity;
import com.example.smart_edu.Model.Class;
import com.example.smart_edu.Model.Student;
import com.example.smart_edu.R;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class info_user extends AppCompatActivity {

    EditText namePhuhuynh, phonePH, emailPH, address, ngay;
    Spinner sp_lophoc, sp_son_name;
    Button bt_add, bt_cancel, bt_date;

    ArrayList<Student> listStudent;
    ArrayList<Class> listClass;

    Calendar cal;
    DatePickerDialog dpd;

    int id_hv, id_class;

    GoogleSignInAccount signInAccount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        Anhxa();
        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        namePhuhuynh.setText(signInAccount.getDisplayName());
        emailPH.setText(signInAccount.getEmail());

        // Đọc và ghi dữ liệu vào arraylist lớp
        listClass = Read_Write_DataClass();

        Spinner_Class(listClass);
        Spinner_Student(listStudent);

    }

    private void showDatePickerDialog() {
        cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        dpd = new DatePickerDialog(info_user.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                ngay.setText(mDayOfMonth +"/"+ mMonth );
            }
        }, day, month, year);
        dpd.show();
    }


    private void Spinner_Class( ArrayList<Class> list){
        ArrayAdapter<Class> adapter = new ArrayAdapter<Class>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sp_lophoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onItemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        Class lophoc = (Class)  adapter.getItem(position);

        id_class = lophoc.getIdClass();
        Read_Write_DataStudent(id_class);
    }

    private void Spinner_Student(ArrayList<Student> listHV){
        ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_spinner_item,listHV);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sp_son_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler1(parent,view,position,id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onItemSelectedHandler1(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        Student HV = (Student) adapter.getItem(position);
        id_hv = HV.getIdHV();
    }


    private ArrayList<Student> Read_Write_DataStudent(int id_class){
        Dataservice dataservice = APIService.getService();
        Call<List<Student>> callback = dataservice.GetStudent(id_class);
        callback.enqueue(new Callback<List<Student>>() {

            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                ArrayList<Student> hocvien = (ArrayList<Student>) response.body();
                for (Student b: hocvien){
                    listStudent.add(b);
                }
            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

                Toast.makeText(info_user.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
        return listStudent;
    }

    private ArrayList<Class> Read_Write_DataClass(){
        Dataservice dataservice = APIService.getService();
        Call<List<Class>> callback = dataservice.GetDataClass();
        callback.enqueue(new Callback<List<Class>>() {

            @Override
            public void onResponse(Call<List<Class>> call, Response<List<Class>> response) {
                ArrayList<Class> hocvien = (ArrayList<Class>) response.body();
                for (Class lop: hocvien){
                    listClass.add(lop);
                }
            }
            @Override
            public void onFailure(Call<List<Class>> call, Throwable t) {

                Toast.makeText(info_user.this, "Lấy dữ liệu thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
        return listClass;
    }

    private void Anhxa() {
        namePhuhuynh = findViewById(R.id.et_id_namePH);
        phonePH = findViewById(R.id.edt_sdt);
        emailPH = findViewById(R.id.edt_email);
        address = findViewById(R.id.et_address);
        ngay = findViewById(R.id.et_date_of_b);
        sp_lophoc = findViewById(R.id.sp_class);
        sp_son_name = findViewById(R.id.sp_name_baby);
        bt_add = findViewById(R.id.bt_info_user);
        bt_add.setText("CẬP NHẬT");

        bt_cancel = findViewById(R.id.bt_cancel_student);
        bt_cancel.setText("HUỶ");

        bt_date = findViewById(R.id.bt_date_of_b);

        listClass = new ArrayList<>();
        listStudent = new ArrayList<>();

    }

    public void info_user(View view) {

//        Integer id_Hv = Integer.parseInt(sp_son_name.toString());
        String nameHV = sp_son_name.toString();
//        Integer id_class = Integer.parseInt(sp_lophoc.toString());
        Integer hocphi = 0;
        String namePH = namePhuhuynh.getText().toString();
        String phone = phonePH.getText().toString();
        String email = emailPH.getText().toString();
        String diachi = address.getText().toString();

        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(ngay.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Student hv = new Student(id_hv, nameHV, id_class, hocphi, namePH, phone, email, diachi, date);

        Dataservice dataservice = APIService.getService();
        dataservice.UpdateHV(id_hv, hv).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(info_user.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(info_user.this, activity_main2.class );
                intent.putExtra("student",hv);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(info_user.this, "Cập nhật thông tin thất bại! \n Vui lòng thử lại sau!!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void Sign_out(){
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut(); // very important if you are using firebase.
                    Intent login_intent = new Intent(info_user.this, MainActivity.class);
                    login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // clear previous task (optional)
                    startActivity(login_intent);
                }
            }
        });
    }

    public void Cancel_student(View view) {
        Sign_out();
    }
}
