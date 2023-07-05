package com.yishak.smm_assessment.activities;

import static com.yishak.smm_assessment.common.Commons.projectUnderConstruction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.adapters.ProjectListAdapter;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.NewProject;
import com.yishak.smm_assessment.model.Project;
import com.yishak.smm_assessment.model._project;
import com.yishak.smm_assessment.network.API;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowDashboard extends AppCompatActivity {
    private ListView projectsList;
    private ProjectListAdapter adapter;
    private ArrayList<BaseTransaction> projectListCollection;
    private ExtendedFloatingActionButton takeTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_dashboard);

        init();
    }

    private void init()
    {
        getAllProjects();

        takeTest = findViewById(R.id.fabTest);
        projectsList = findViewById(R.id.lstDashboardProjectList);

        projectListCollection = new ArrayList();
        projectUnderConstruction = new BaseTransaction();

        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(windowDashboard.this, windowCreateProject.class);
                startActivity(intent);
            }
        });
    }

    private void getAllProjects(){
        API.getAllProjects().getAllProjects()
                .enqueue(new Callback<ArrayList<BaseTransaction>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BaseTransaction>> call, Response<ArrayList<BaseTransaction>> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            projectListCollection = response.body();
                            inflateList(projectListCollection);
                        }
                        else{
                            Log.i("Error", "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<BaseTransaction>> call, Throwable t) {
                        Log.i("Response", t.getMessage());
                    }
                });
    }
    private void inflateList(List<BaseTransaction> projects)
    {
        adapter = new ProjectListAdapter(this, projects);
        projectsList.setAdapter(adapter);

        projectsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectUnderConstruction = (BaseTransaction) adapter.getItem(i);

                Commons.newProjectList = new ArrayList<>();
                NewProject project = new NewProject();
                project.setId(projectUnderConstruction.get_id());

                Commons.newProjectList.add(project);

                Intent intent = new Intent(windowDashboard.this, windowResult.class);
                startActivity(intent);
            }
        });
    }
}