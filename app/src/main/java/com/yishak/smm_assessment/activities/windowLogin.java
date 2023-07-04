package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.common.Commons;

import java.util.ArrayList;

public class windowLogin extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtRegister;
    private SpannableString spannableString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_login);

        Commons.commonBufferList = new ArrayList<>();
        Commons.newProjectList = new ArrayList<>();

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        String mString = "Register";
        spannableString = new SpannableString(mString);
        spannableString.setSpan(new UnderlineSpan(), 0, mString.length(), 0);
        txtRegister.setText(mString);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                startActivity(intent);
            }
        });
    }
}