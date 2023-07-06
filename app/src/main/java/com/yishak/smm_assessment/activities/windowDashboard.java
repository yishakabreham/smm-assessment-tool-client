package com.yishak.smm_assessment.activities;

import static com.yishak.smm_assessment.common.Commons.bT;
import static com.yishak.smm_assessment.common.Commons.projectUnderConstruction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowDashboard extends AppCompatActivity {
    private ListView projectsList;
    private ProjectListAdapter adapter;
    private ArrayList<BaseTransaction> projectListCollection;
    private ExtendedFloatingActionButton takeTest, logout;
    boolean doubleBackToExitPressedOnce = false;
    private TextView count;
    TextView currentDate;
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
        logout = findViewById(R.id.fabOut);
        projectsList = findViewById(R.id.lstDashboardProjectList);
        currentDate = findViewById(R.id.dateEn);
        count = findViewById(R.id.txtPreviousProjects);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);

        currentDate.setText("Date and Time: " + DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));

        projectListCollection = new ArrayList();
        projectUnderConstruction = new BaseTransaction();
        bT = new ArrayList<>();

        Commons.newProjectList = new ArrayList<>();
        Commons.commonBufferList = new ArrayList<>();

        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(windowDashboard.this, windowCreateProject.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(windowDashboard.this, windowBestPractices.class);
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
                            bT = projectListCollection;
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

        count.setText("Previous Project Assessments (Count: " + projects.size() + ")");

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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}