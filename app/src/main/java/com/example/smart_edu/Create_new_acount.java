package com.example.smart_edu;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Create_new_acount extends AppCompatActivity {
    EditText Email, Pass, Repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_acount);

        Anhxa();
    }

    private void Anhxa() {
        Email = findViewById(R.id.edt_email);
        Pass = findViewById(R.id.edt_Pass);
        Repeat = findViewById(R.id.edt_repeat);

    }

    public void Create_new(View view) {

    }
}
