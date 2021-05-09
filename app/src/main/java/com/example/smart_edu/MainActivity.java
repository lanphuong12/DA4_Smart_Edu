package com.example.smart_edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smart_edu.Admin.activity_main1;
import com.example.smart_edu.Model.Student;
import com.example.smart_edu.Model.User;
import com.example.smart_edu.Server.APIService;
import com.example.smart_edu.Server.Dataservice;
import com.example.smart_edu.User.activity_main2;
import com.example.smart_edu.User.info_user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText User, Pass;
    Button Signin_GG;

    Student student;

    GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;


    ArrayList<User> tk_user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Anhxa();
        GetdataUser();

        // Định cấu hình ứng dụng khách Google
        createRequest();
    }

    private void createRequest() {
        // Định cấu hình Đăng nhập bằng Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // đối với requestIdToken, đây là trong tệp giá trị.xml
                // được tạo từ google-services.json của bạn
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Xây dựng GoogleSignInClient với các tùy chọn do gso chỉ định.
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
    }

    private void Anhxa() {
        User = findViewById(R.id.ed_user);
        Pass = findViewById(R.id.ed_pass);
        Signin_GG = findViewById(R.id.google_signIn);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void Log_in(View view) {

    }

    public void Login_with_GG(View view) {
        signIn();
    }

    public void Create_new(View view) {
        Intent intent = new Intent(MainActivity.this, Create_new_acount.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount accountGG = task.getResult(ApiException.class);
//                Toast.makeText(this, "Account hợp lệ!", Toast.LENGTH_SHORT).show();

                boolean check = true;
                for (int i = 0; i < tk_user.size(); i++) {
                    if (tk_user.get(i).getEmail().equals(accountGG.getEmail())) {
                        check = false;
                    }
                }
                while (check == true) {
                    Dataservice datasv = APIService.getService();
                    datasv.addTK(accountGG.getEmail(), "user").enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            tk_user.clear();
                            GetdataUser();
                            Toast.makeText(MainActivity.this, "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Thêm tài khoản thất bại!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                firebaseAuthWithGoogle(accountGG.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GetdataUser() {
        Dataservice dataservice = APIService.getService();
        Call<List<User>> callback = dataservice.GetDataUser();
        callback.enqueue(new Callback<List<com.example.smart_edu.Model.User>>() {
            @Override
            public void onResponse(Call<List<com.example.smart_edu.Model.User>> call, Response<List<com.example.smart_edu.Model.User>> response) {
                tk_user = (ArrayList<User>) response.body();
//                Toast.makeText(MainActivity.this, "" + tk_user.size(), Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity.this, "Lấy dữ liệu người dùng thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<com.example.smart_edu.Model.User>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Lấy dữ liệu người dùng thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
////            Intent intent = new Intent(getApplicationContext(), activity_main1.class);
////            startActivity(intent);
//            updateUI(user);
//        }
//
//    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "aaaaaaa", Toast.LENGTH_LONG).show();
                            updateUI(user);
//                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Xin lỗi! ERROR!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser fuser) {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        for (int i = 0; i < tk_user.size(); i++) {
            Toast.makeText(this, "aaa", Toast.LENGTH_LONG).show();
            while (tk_user.get(i).getEmail().equals(fuser.getEmail())) {
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                if (tk_user.get(i).getTypePersional().equals("admin")) {
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), activity_main1.class);
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                    String ema = fuser.getEmail().replace(".","__");
                    Toast.makeText(this, "" + ema,  Toast.LENGTH_LONG).show();
                    Dataservice dataservice1 = APIService.getService();
                    Call<Student> callback1 = dataservice1.GetStudentEmail(ema);
                    callback1.enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            Student hv = response.body();
                            if (response.body() != null) {
                                Intent intent2 = new Intent(MainActivity.this, activity_main2.class);
                                intent2.putExtra("student", hv);
                            } else {
                                Intent intent4 = new Intent(MainActivity.this, info_user.class);
                                startActivity(intent4);
                            }
//                                    ArrayList<Student> students = (ArrayList<Student>) response.body();
//                                    for (Student hv : students) {
//                                        hocvien.add(hv);
//                                    }
//                                    for (int i = 0; i < hocvien.size(); i++) {
//                                        if (hocvien.get(i).getEmail() != null) {
//                                            if (hocvien.get(i).getEmail().equals(account.getEmail())) {
//                                                student = hocvien.get(i);
//                                                Intent intent2 = new Intent(getApplicationContext(), activity_main2.class);
//                                                intent2.putExtra("student", hocvien.get(i));
//                                                startActivity(intent2);
//                                            }
//                                        } else {
//                                            Intent intent4 = new Intent(getApplicationContext(), info_user.class);
//                                            startActivity(intent4);
//                                        }
//                                    }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "LOG IN UNSUCCESFUL!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }
    }

}