package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.activities.phases.windowRequirements;
import com.yishak.smm_assessment.activities.phases.windowTesting;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.common.Shared;
import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.model.NewProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class windowCreateProject extends AppCompatActivity {

    private ExtendedFloatingActionButton btnCreateProject;
    final Calendar myCalendar= Calendar.getInstance();
    private int dateSelectionFlag = 0;
    public static NewProject newProject;
    private String pName, pClient, pSDate, pEDate, pRemark;
    private EditText txtProjectName, txtProjectClient, txtRemark;
    private EditText txtProjectDate, txtProjectEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_create_project);

        init();
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbCreateProject);
        toolbar.setTitle("Create a Project");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtProjectName = findViewById(R.id.txtProjectName);
        txtProjectClient = findViewById(R.id.txtClientName);
        txtProjectDate = findViewById(R.id.txtProjectStartDate);
        txtProjectEnd = findViewById(R.id.txtProjectEndDate);
        txtRemark = findViewById(R.id.txtOther);

        btnCreateProject = findViewById(R.id.fabCreateProject);
        btnCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate(String.valueOf(txtProjectName.getText()), String.valueOf(txtProjectClient.getText()),
                        String.valueOf(txtProjectDate.getText()), String.valueOf(txtProjectEnd.getText()), String.valueOf(txtRemark.getText()));
                if (isOk)
                {
                    newProject = new NewProject(pName, pClient, pSDate, pEDate, pRemark);
                    Commons.newProjectList.add(newProject);

                    Intent intent = new Intent(windowCreateProject.this, windowRequirements.class);
                    startActivity(intent);
                }
                else
                {
                    new MaterialAlertDialogBuilder(windowCreateProject.this, R.style.AlertDialogTheme)
                            .setTitle("Error")
                            .setMessage("Please ensure that you entered all the required fields.")
                            .setNegativeButton("GOT IT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
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
        txtProjectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(windowCreateProject.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateSelectionFlag = 1;
            }
        });
        txtProjectEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(windowCreateProject.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateSelectionFlag = 2;
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        if(dateSelectionFlag == 0){}
        else if(dateSelectionFlag == 1)
        {
            txtProjectDate.setText(dateFormat.format(myCalendar.getTime()));
            dateSelectionFlag = 0;
        }
        else if(dateSelectionFlag == 2)
        {
            txtProjectEnd.setText(dateFormat.format(myCalendar.getTime()));
            dateSelectionFlag = 0;
        }
    }
    private boolean validate(String projectName, String client, String start, String end, String additional)
    {
        if(projectName != null && !projectName.equals("") && client != null && !client.equals("") && start != null && !start.equals(""))
        {
            pName = projectName;
            pClient = client;
            pSDate = start;
            pEDate = end;
            pRemark = additional;

            return true;
        }

        else return false;
    }
}