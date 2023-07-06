package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    private TextView txtRegister, userName, password;
    private SpannableString spannableString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_login);

        Commons.commonBufferList = new ArrayList<>();
        Commons.newProjectList = new ArrayList<>();

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        userName = findViewById(R.id.txtUserName);
        password = findViewById(R.id.txtPassword);

        String mString = "Register";
        spannableString = new SpannableString(mString);
        spannableString.setSpan(new UnderlineSpan(), 0, mString.length(), 0);
        txtRegister.setText(mString);

        userName.setText("admin");
        password.setText("admin");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(validate())
                {
                    Intent intent = new Intent(windowLogin.this, windowDashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    new MaterialAlertDialogBuilder(windowLogin.this, R.style.AlertDialogTheme)
                            .setTitle("Error")
                            .setMessage("The typed username or password is incorrect.")
                            .setNegativeButton("GOT IT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
            }
        });

        _details();
    }

    private boolean validate()
    {
        String uN, pW;
        boolean result = false;
        if(userName != null && password != null)
        {
            uN = String.valueOf(userName.getText());
            pW = String.valueOf(password.getText());

            if(uN.equals("admin") && pW.equals("admin"))
            {
                result = true;
            }
        }
        return result;
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