package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

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

public class windowSubPractices extends AppCompatActivity {
    public ArrayList<_Phase> phaseList;
    public _Phase requirementEngineeringPhase;
    public _Phase designPhase;
    public _Phase implementationPhase;
    public _Phase deploymentPhase;
    public _Phase testingPhase;
    private ListView subPracticeListView;
    private SubPracticeAdapter adapter;
    Button btnAddMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_sub_practices);

        Toolbar toolbar = findViewById(R.id.tbSubPractices);
        toolbar.setTitle("Confirmation");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        subPracticeListView = findViewById(R.id.lstSubPractices);
        details();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(windowSubPractices.this, MainActivity.class);
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