package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.activities.phases.windowRequirements;
import com.yishak.smm_assessment.model.NewProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class windowCreateProject extends AppCompatActivity {

    private Button projectStartDate, projectCompletionDate;
    private ExtendedFloatingActionButton btnCreateProject;
    final Calendar myCalendar= Calendar.getInstance();
    private int dateSelectionFlag = 0;
    public static NewProject newProject;
    private String name, client, date;
    private TextView txtProjectName, txtProjectClient, txtProjectDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_create_project);

        init();
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbCreateProject);
        toolbar.setTitle("Add a Project");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        projectStartDate = findViewById(R.id.txtProjectStartDate);
        projectCompletionDate = findViewById(R.id.txtProjectEndDate);

        txtProjectName = findViewById(R.id.txtItemProjectName);
        txtProjectClient = findViewById(R.id.txtItemProjectClient);
        txtProjectDate = findViewById(R.id.txtItemDateCreated);

        btnCreateProject = findViewById(R.id.fabCreateProject);
        btnCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate();
                if (isOk)
                {
                    newProject = new NewProject(name, client, date);
                    Intent intent = new Intent(windowCreateProject.this, windowRequirements.class);
                    startActivity(intent);
                }
                else
                {
                    //show something to the user
                }
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        projectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(windowCreateProject.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateSelectionFlag = 1;
            }
        });
        projectCompletionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(windowCreateProject.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateSelectionFlag = 2;
            }
        });
    }
    private void updateLabel(){
        String myFormat="dd/mm/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        if(dateSelectionFlag == 0){}
        else if(dateSelectionFlag == 1)
        {
            projectStartDate.setText(dateFormat.format(myCalendar.getTime()));
            dateSelectionFlag = 0;
        }
        else if(dateSelectionFlag == 2)
        {
            projectCompletionDate.setText(dateFormat.format(myCalendar.getTime()));
            dateSelectionFlag = 0;
        }
    }
    private boolean validate(){
        //do validation here
        boolean validated = true;

        name = "Dummy Name";
        client = "Dummy Client";
        date = "Dummy Date";

        if(validated) return true;
        else return false;
    }
}