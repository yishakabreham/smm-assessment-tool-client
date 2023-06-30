package com.yishak.smm_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yishak.smm_assessment.activities.windowSubPractices;

public class MainActivity extends AppCompatActivity {

    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ok = findViewById(R.id.btnOk);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, windowSubPractices.class);
//                startActivity(intent);
//            }
//        });
    }
}