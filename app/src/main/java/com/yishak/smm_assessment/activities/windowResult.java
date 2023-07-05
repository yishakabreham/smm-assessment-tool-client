package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.adapters.PhaseAdapter;
import com.yishak.smm_assessment.adapters.ProjectListAdapter;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.common.Shared;
import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.model.Business;
import com.yishak.smm_assessment.model.PhaseAdapterDto;
import com.yishak.smm_assessment.model._project;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._Phase;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.reflect.KType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowResult extends AppCompatActivity {

    private TextView projectName, clientName, startDate, endDate, assessedBy;
    private TextView overallResult;
    private BaseTransaction baseTransaction;
    private ArrayList<_Phase> phases;
    private PhaseAdapter adapter;
    private ListView phaseListView;
    private ArrayList<PhaseAdapterDto> phaseAdapterDtoArrayList;
    private ExtendedFloatingActionButton btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_result);

        init();
        businessLogic();
    }

    private void init()
    {
        projectName = findViewById(R.id.txtResultProjectName);
        clientName = findViewById(R.id.txtResultClientName);
        startDate = findViewById(R.id.txtResultStartDate);
        assessedBy = findViewById(R.id.txtResultEndDate);
        endDate = findViewById(R.id.txtResultEndDate);

        overallResult = findViewById(R.id.txtOverllML);
        btnFinish = findViewById(R.id.fabResultFinish);

        Toolbar toolbar = findViewById(R.id.tbResult);
        toolbar.setTitle("Assessment Result");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        phaseAdapterDtoArrayList = new ArrayList<>();
        phaseListView = findViewById(R.id.lstResultList);

        if(Commons.phaseList != null) phases = Commons.phaseList;
        if(Commons.projectUnderConstruction == null) details();
        else baseTransaction = Commons.projectUnderConstruction;

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(windowResult.this, windowDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void inflate()
    {
        adapter = new PhaseAdapter(this, phaseAdapterDtoArrayList);
        phaseListView.setAdapter(adapter);
    }

    private void businessLogic()
    {
        projectName.setText("Project - " + baseTransaction.getProjectName());
        clientName.setText("Client - " + baseTransaction.getProjectName());
        startDate.setText("Start - " + baseTransaction.getProjectStartDate());
        endDate.setText("Status - Assessment Completed" );

        List<Buffer> bufferList = baseTransaction.getBufferList();
        if(bufferList != null && bufferList.size() > 0)
        {
            List<Buffer> rE = bufferList.stream().filter(x -> x.getType().equals("rE")).collect(Collectors.toList());
            List<Buffer> dE = bufferList.stream().filter(x -> x.getType().equals("dE")).collect(Collectors.toList());
            List<Buffer> iM = bufferList.stream().filter(x -> x.getType().equals("iM")).collect(Collectors.toList());
            List<Buffer> dEP = bufferList.stream().filter(x -> x.getType().equals("dEP")).collect(Collectors.toList());
            List<Buffer> tE = bufferList.stream().filter(x -> x.getType().equals("tE")).collect(Collectors.toList());

            List<Business> rEBusinesses;
            List<Business> dEBusinesses;
            List<Business> iMBusinesses;
            List<Business> dEPBusinesses;
            List<Business> tEBusinesses;

            rEBusinesses = filter(rE, "rE");
            dEBusinesses = filter(dE, "dE");
            iMBusinesses = filter(iM, "iM");
            dEPBusinesses = filter(dEP, "dEP");
            tEBusinesses = filter(tE, "tE");

            int rEResult = refactored(rEBusinesses, "rE");
            int dEResult = refactored(dEBusinesses, "dE");
            int iMResult = refactored(iMBusinesses, "iM");
            int dEPResult = refactored(dEPBusinesses, "dEP");
            int tEResult = refactored(tEBusinesses, "tE");

            int overall = (rEResult + dEResult + iMResult + dEPResult + tEResult) / 5;
            String desc = "";
            switch (overall)
            {
                case 1:
                    desc = "Very Low";
                    break;
                case 2:
                    desc = "Low";
                    break;
                case 3:
                    desc = "Moderate";
                    break;
                case 4:
                    desc = "High";
                    break;
                case 5:
                    desc = "Very High";
                    break;
            }
            overallResult.setText("Level " + overall + "/5 (" + desc + ")");

            inflate();
        }
    }

    private int refactored(List<Business> businessList, String type)
    {
        PhaseAdapterDto dto = new PhaseAdapterDto();

        int result = 1;
        if(businessList != null)
        {
            double totalSize;
            double eL3;
            double percentageL1 = 0;
            double percentageL2 = 0;
            double percentageL3 = 0;
            double percentageL4 = 0;
            double percentageL5 = 0;

            List<Business> level1, level2, level3, level4, level5;

            level1 = businessList.stream().filter(x -> x.geteL() == 1).collect(Collectors.toList());
            if(level1 != null)
            {
                totalSize = level1.size();
                eL3 = level1.stream().filter(y -> y.getiL() >= 3).collect(Collectors.toList()).size();

                percentageL1 = (eL3 / totalSize) * 100;

                if(percentageL1 >= 50 || level1.size() == 0)
                {
                    level2 = businessList.stream().filter(x -> x.geteL() == 2).collect(Collectors.toList());
                    if(level2 != null)
                    {
                        totalSize = level2.size();
                        eL3 = level2.stream().filter(y -> y.getiL() >= 3).collect(Collectors.toList()).size();

                        percentageL2 = (eL3 / totalSize) * 100;

                        if(percentageL2 >= 50 || level2.size() == 0)
                        {
                            level3 = businessList.stream().filter(x -> x.geteL() == 3).collect(Collectors.toList());
                            if(level3 != null) {
                                totalSize = level3.size();
                                eL3 = level3.stream().filter(y -> y.getiL() >= 3).collect(Collectors.toList()).size();

                                percentageL3 = (eL3 / totalSize) * 100;

                                if(percentageL3 >= 50 || level3.size() == 0)
                                {
                                    level4 = businessList.stream().filter(x -> x.geteL() == 4).collect(Collectors.toList());
                                    if(level4 != null) {
                                        totalSize = level4.size();
                                        eL3 = level4.stream().filter(y -> y.getiL() >= 3).collect(Collectors.toList()).size();

                                        percentageL4 = (eL3 / totalSize) * 100;

                                        if(percentageL4 >= 50 || level4.size() == 0)
                                        {
                                            level5 = businessList.stream().filter(x -> x.geteL() == 5).collect(Collectors.toList());
                                            if(level5 != null) {
                                                totalSize = level5.size();
                                                eL3 = level5.stream().filter(y -> y.getiL() >= 3).collect(Collectors.toList()).size();

                                                percentageL5 = (eL3 / totalSize) * 100;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(percentageL5 >= 50){
                result = 5;
            }
            else if(percentageL4 >= 50){
                result = 4;
            }
            else if(percentageL3 >= 50){
                result = 3;
            }
            else if(percentageL2 >= 50){
                result = 2;
            }
            else if(percentageL1 >= 50){
                result = 1;
            }
            else result = 1;
        }
        switch (type)
        {
            case "rE":
                dto.setName("Requirements Engineering");
                break;
            case "dE":
                dto.setName("Design");
                break;
            case "iM":
                dto.setName("Implementation");
                break;
            case "dEP":
                dto.setName("Deployment");
                break;
            case "tE":
                dto.setName("Tetsing");
                break;
        }
        dto.setCode("0");
        dto.setmL(String.valueOf(result));

        phaseAdapterDtoArrayList.add(dto);

        return result;
    }
    private List<Business> filter(List<Buffer> bufferList, String type)
    {
        List<Business> businessList = new ArrayList<>();
        switch (type)
        {
            case "rE":
                for (Buffer buffer : bufferList)
                {
                    _SubPractice subPractice = Commons.requirementEngineeringPhase.getPractices().get(0).getSubPractices().stream()
                            .filter(x -> x.getSub_practice_id() == buffer.getCode()).findFirst().get();

                    Business business = new Business();
                    business.setCode(buffer.getCode());
                    business.setType(buffer.getType());
                    business.seteL(subPractice.getEvolution_level());
                    business.setiL(buffer.getiL());

                    businessList.add(business);
                }
                break;
            case "dE":
                for (Buffer buffer : bufferList)
                {
                    _SubPractice subPractice = Commons.designPhase.getPractices().get(0).getSubPractices().stream()
                            .filter(x -> x.getSub_practice_id() == buffer.getCode()).findFirst().get();

                    Business business = new Business();
                    business.setCode(buffer.getCode());
                    business.setType(buffer.getType());
                    business.seteL(subPractice.getEvolution_level());
                    business.setiL(buffer.getiL());

                    businessList.add(business);
                }
                break;
            case "iM":
                for (Buffer buffer : bufferList)
                {
                    _SubPractice subPractice = Commons.implementationPhase.getPractices().get(0).getSubPractices().stream()
                            .filter(x -> x.getSub_practice_id() == buffer.getCode()).findFirst().get();

                    Business business = new Business();
                    business.setCode(buffer.getCode());
                    business.setType(buffer.getType());
                    business.seteL(subPractice.getEvolution_level());
                    business.setiL(buffer.getiL());

                    businessList.add(business);
                }
                break;
            case "dEP":
                for (Buffer buffer : bufferList)
                {
                    _SubPractice subPractice = Commons.deploymentPhase.getPractices().get(0).getSubPractices().stream()
                            .filter(x -> x.getSub_practice_id() == buffer.getCode()).findFirst().get();

                    Business business = new Business();
                    business.setCode(buffer.getCode());
                    business.setType(buffer.getType());
                    business.seteL(subPractice.getEvolution_level());
                    business.setiL(buffer.getiL());

                    businessList.add(business);
                }
                break;
            case "tE":
                for (Buffer buffer : bufferList)
                {
                    _SubPractice subPractice = Commons.testingPhase.getPractices().get(0).getSubPractices().stream()
                            .filter(x -> x.getSub_practice_id() == buffer.getCode()).findFirst().get();

                    Business business = new Business();
                    business.setCode(buffer.getCode());
                    business.setType(buffer.getType());
                    business.seteL(subPractice.getEvolution_level());
                    business.setiL(buffer.getiL());

                    businessList.add(business);
                }
                break;
        }
        return businessList;
    }

    private void details(){
        _project project = new _project();
        project.setId(String.valueOf(Commons.newProjectList.get(0).getId()));

        API.getProject().getProject(project)
                .enqueue(new Callback<BaseTransaction>() {
                    @Override
                    public void onResponse(Call<BaseTransaction> call, Response<BaseTransaction> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            baseTransaction = response.body();
                        }
                        else{
                            Log.i("Error", "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseTransaction> call, Throwable t) {
                        Log.i("Response", t.getMessage());
                    }
                });
    }
}