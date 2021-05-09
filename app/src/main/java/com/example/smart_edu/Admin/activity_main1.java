package com.example.smart_edu.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smart_edu.Admin.QlyHocvien.QlyHV_main;
import com.example.smart_edu.Admin.QlyLop.QlyLop_main;
import com.example.smart_edu.Admin.QlySach.QlySach_main;
import com.example.smart_edu.MainActivity;
import com.example.smart_edu.R;
import com.example.smart_edu.User.activity_main2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class activity_main1 extends AppCompatActivity {


    Button QlyLop, QlyHV, QlySach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();
        MethodToolbar();

    }

    public void MethodToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TRANG CHỦ");
        toolbar.setSubtitle("Smart Edu");
        toolbar.inflateMenu(R.menu.log_out);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.logout){
                    Sign_out();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out, menu);
        return true;
    }
    private void Anhxa() {
        QlyLop = findViewById(R.id.bt_Qly_lop);
        QlyHV = findViewById(R.id.bt_Qly_hocvien);
        QlySach = findViewById(R.id.bt_Qly_sach);

    }

    public void QlyLop(View view) {

        Intent intent = new Intent(activity_main1.this, QlyLop_main.class);
        startActivity(intent);
    }

    public void QlyHS(View view) {
        Intent intent = new Intent(activity_main1.this, QlyHV_main.class);
        startActivity(intent);
    }

    public void QlySach(View view) {
        Intent intent = new Intent(activity_main1.this, QlySach_main.class);
        startActivity(intent);
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
                    Intent login_intent = new Intent(activity_main1.this, MainActivity.class);
                    login_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); // clear previous task (optional)
                    startActivity(login_intent);
                }
            }
        });
    }
}
