package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yishak.smm_assessment.R;

public class windowDashboard extends AppCompatActivity {

    private ExtendedFloatingActionButton takeTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_dashboard);

        init();
    }

    private void init()
    {
        takeTest = findViewById(R.id.fabTest);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(windowDashboard.this, windowCreateProject.class);
                startActivity(intent);
            }
        });
    }
}