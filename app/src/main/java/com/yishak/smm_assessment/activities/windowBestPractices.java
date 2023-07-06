package com.yishak.smm_assessment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.adapters.BestPracticeAdapter;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.model.Investment;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class windowBestPractices extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_best_practices);

        Toolbar toolbar = findViewById(R.id.tbBest);
        toolbar.setTitle("Best Practices");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ExpandableListView elvInvestments = (ExpandableListView) findViewById(R.id.elvInvestments);

        // Create the groups
        final List<String> lstGroups = new ArrayList<>();
        lstGroups.add("Requirements Engineering");
        lstGroups.add("Design");
        lstGroups.add("Implementation");
        lstGroups.add("Deployment");
        lstGroups.add("Testing");


        // Create items of each group
        List<Investment> rE = new ArrayList<>();
        List<Investment> dE = new ArrayList<>();
        List<Investment> iM = new ArrayList<>();
        List<Investment> dEP = new ArrayList<>();
        List<Investment> tE = new ArrayList<>();

        List<_SubPractice> subPracticesRE = Commons.requirementEngineeringPhase.getPractices().get(0).getSubPractices();
        List<_SubPractice> subPracticesDE = Commons.designPhase.getPractices().get(0).getSubPractices();
        List<_SubPractice> subPracticesIM = Commons.implementationPhase.getPractices().get(0).getSubPractices();
        List<_SubPractice> subPracticesDEP = Commons.deploymentPhase.getPractices().get(0).getSubPractices();
        List<_SubPractice> subPracticesTE = Commons.testingPhase.getPractices().get(0).getSubPractices();

        for (int i = 0; i < subPracticesRE.size(); i++)
        {
            rE.add(new Investment(subPracticesRE.get(i).getDescription(), 0, 0));
        }
        for (int i = 0; i < subPracticesDE.size(); i++)
        {
            dE.add(new Investment(subPracticesDE.get(i).getDescription(), 0, 0));
        }
        for (int i = 0; i < subPracticesIM.size(); i++)
        {
            iM.add(new Investment(subPracticesIM.get(i).getDescription(), 0, 0));
        }
        for (int i = 0; i < subPracticesDEP.size(); i++)
        {
            dEP.add(new Investment(subPracticesDEP.get(i).getDescription(), 0, 0));
        }
        for (int i = 0; i < subPracticesTE.size(); i++)
        {
            tE.add(new Investment(subPracticesTE.get(i).getDescription(), 0, 0));
        }

        final HashMap<String, List<Investment>> lstItemsGroup = new HashMap<>();
        lstItemsGroup.put(lstGroups.get(0), rE);
        lstItemsGroup.put(lstGroups.get(1), dE);
        lstItemsGroup.put(lstGroups.get(2), iM);
        lstItemsGroup.put(lstGroups.get(3), dEP);
        lstItemsGroup.put(lstGroups.get(4), tE);

        final BestPracticeAdapter listViewAdapter = new BestPracticeAdapter(this, lstGroups, lstItemsGroup);
        elvInvestments.setAdapter(listViewAdapter);
    }
}