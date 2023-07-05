package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._Phase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        _details();
    }

    public void _details(){
        Commons.phaseList = new ArrayList<>();
        API.detail().getDetails()
                .enqueue(new Callback<ArrayList<_Phase>>() {
                    @Override
                    public void onResponse(Call<ArrayList<_Phase>> call, Response<ArrayList<_Phase>> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            Commons.phaseList = response.body();
                            if(Commons.phaseList != null && Commons.phaseList.size() > 0)
                            {
                                for (int i = 0; i < Commons.phaseList.size(); i++)
                                {
                                    String phaseLabel = Commons.phaseList.get(i).getName();
                                    if(phaseLabel != null){
                                        switch (phaseLabel)
                                        {
                                            case "requirement_engineering":
                                                Commons.requirementEngineeringPhase = Commons.phaseList.get(i);
                                                break;
                                            case "design":
                                                Commons.designPhase = Commons.phaseList.get(i);
                                                break;
                                            case "implementation":
                                                Commons.implementationPhase = Commons.phaseList.get(i);
                                                break;
                                            case "deployment":
                                                Commons.deploymentPhase = Commons.phaseList.get(i);
                                                break;
                                            case "testing":
                                                Commons.testingPhase = Commons.phaseList.get(i);
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<_Phase>> call, Throwable t) {
                        Log.i("Failure", t.getMessage());
                    }
                });
    }
}