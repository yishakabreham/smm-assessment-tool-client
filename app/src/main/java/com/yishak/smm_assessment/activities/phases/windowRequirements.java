package com.yishak.smm_assessment.activities.phases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yishak.smm_assessment.MainActivity;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.adapters.SubPracticeAdapter;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._Phase;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowRequirements extends AppCompatActivity {
    public static ArrayList<_Phase> phaseList;
    public _Phase requirementEngineeringPhase;
    public _Phase designPhase;
    public _Phase implementationPhase;
    public _Phase deploymentPhase;
    public _Phase testingPhase;
    private ListView subPracticeListView;
    private SubPracticeAdapter adapter;
    private ExtendedFloatingActionButton btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_sub_practices);

        init();
        details();
    }

    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbSubPractices);
        toolbar.setTitle("Requirements Phase");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        subPracticeListView = findViewById(R.id.lstSubPractices);
        btnNext = findViewById(R.id.fabSubNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate();
                if (isOk)
                {
                    Intent intent = new Intent(windowRequirements.this, windowDesign.class);
                    startActivity(intent);
                }
                else
                {
                    //show something to the user
                }
            }
        });
    }
    private boolean validate(){
        //do validation here
        boolean validated = true;

        if(validated) return true;
        else return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(windowRequirements.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void details(){
        API.detail().getDetails()
                .enqueue(new Callback<ArrayList<_Phase>>() {
                    @Override
                    public void onResponse(Call<ArrayList<_Phase>> call, Response<ArrayList<_Phase>> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            phaseList = response.body();
                            if(phaseList != null && phaseList.size() > 0)
                            {
                                for (int i = 0; i < phaseList.size(); i++)
                                {
                                    String phaseLabel = phaseList.get(i).getName();
                                    if(phaseLabel != null){
                                        switch (phaseLabel)
                                        {
                                            case "requirement_engineering":
                                                requirementEngineeringPhase = phaseList.get(i);
                                                break;
                                            case "design":
                                                designPhase = phaseList.get(i);
                                                break;
                                            case "implementation":
                                                implementationPhase = phaseList.get(i);
                                                break;
                                            case "deployment":
                                                deploymentPhase = phaseList.get(i);
                                                break;
                                            case "testing":
                                                testingPhase = phaseList.get(i);
                                                break;
                                        }
                                    }
                                }
                            }
                            if(requirementEngineeringPhase != null)
                            {
                                inflateList(requirementEngineeringPhase.getPractices().get(0).getSubPractices());
//                                btnAddMore = findViewById(R.id.btn_New);
//                                subPracticeListView.addFooterView(btnAddMore);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<_Phase>> call, Throwable t) {
                        Log.i("Failure", t.getMessage());
                    }
                });
    }

    private void inflateList(List<_SubPractice> subPractices)
    {
        adapter = new SubPracticeAdapter(this, subPractices);
        subPracticeListView.setAdapter(adapter);
    }
}