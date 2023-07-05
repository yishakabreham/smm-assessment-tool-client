package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.adapters.ProjectListAdapter;
import com.yishak.smm_assessment.model.NewProject;
import com.yishak.smm_assessment.model.Project;

import java.util.ArrayList;
import java.util.List;

public class windowProjectList extends AppCompatActivity
{
    private RelativeLayout rLNoProjectYet;
    private ListView projectsList;
    private ProjectListAdapter adapter;
    private ArrayList<Project> projectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_project_list);

        init();
    }

    private void init()
    {
        rLNoProjectYet = findViewById(R.id.rLNoProject);
        rLNoProjectYet.setVisibility(View.GONE);

        projectsList = findViewById(R.id.lstProjectList);

        projectList = new ArrayList();

        NewProject newProject = windowCreateProject.newProject;
        Project project = new Project();
        project.setProjectName(newProject.getProjectName());
        project.setProjectClient(newProject.getProjectClient());
        project.setDateCreated(newProject.getDateCreated());

        projectList.add(project);

        inflateList(projectList);
    }
    private void inflateList(List<Project> projects)
    {
//        adapter = new ProjectListAdapter(this, projects);
//        projectsList.setAdapter(adapter);
    }
}