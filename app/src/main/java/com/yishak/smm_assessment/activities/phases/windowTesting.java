package com.yishak.smm_assessment.activities.phases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.common.Shared;
import com.yishak.smm_assessment.interfaces.RadioButtonClickListener;
import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.model.NewProject;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowTesting extends AppCompatActivity implements RadioButtonClickListener {
    private ListView testingPhaseListView;
    private ExtendedFloatingActionButton btnNext;
    private TestingPhaseListAdapter adapter;
    private HashMap<Integer, String> mapBuffer;
    private List<_SubPractice> testingPhaseSubPractices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_testing);

        init();
        inflateList(windowRequirements.testingPhase.getPractices().get(0).getSubPractices());
    }

    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbTestingPhase);
        toolbar.setTitle("Testing Phase");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        testingPhaseSubPractices = windowRequirements.testingPhase.getPractices().get(0).getSubPractices();

        testingPhaseListView = findViewById(R.id.lstTestingPhase);
        btnNext = findViewById(R.id.fabTestingNext);

        mapBuffer = new HashMap<>();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate();
                if (isOk)
                {
                    new MaterialAlertDialogBuilder(windowTesting.this, R.style.AlertDialogTheme)
                            .setTitle("Confirmation")
                            .setMessage("Please ensure that your choices are definitive.")
                            .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Shared.createBuffer(mapBuffer, testingPhaseSubPractices, "tE");
                                    post();

                                    Toast.makeText(windowTesting.this, String.valueOf(Commons.commonBufferList.size()), Toast.LENGTH_SHORT).show();
                                    for(Buffer buffer : Commons.commonBufferList)
                                    {
                                        Log.i(buffer.getType(), buffer.getCode() + " " + buffer.getiL());
                                    }
                                }
                            })
                            .setNegativeButton("CHECK AGAIN", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
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

    private void post()
    {
        NewProject project = Commons.newProjectList.get(0);
        BaseTransaction baseTransaction = new BaseTransaction();

        baseTransaction.setProjectName(project.getProjectName());
        baseTransaction.setProjectClient(project.getProjectClient());
        baseTransaction.setProjectStartDate(project.getDateCreated());
        baseTransaction.setProjectEndDate(project.getProjectEndDate());
        baseTransaction.setProjectRemark(project.getRemark());

        baseTransaction.setBufferList(Commons.commonBufferList);

        API.postTransaction().saveTransaction(baseTransaction)
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {

                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
    }
    private void inflateList(List<_SubPractice> subPractices)
    {
        adapter = new TestingPhaseListAdapter(getApplicationContext(), subPractices, this);
        testingPhaseListView.setAdapter(adapter);

        testingPhaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onRadioButtonClicked(int count, HashMap<Integer, String> map) {
        int total = testingPhaseSubPractices.size();
        TextView textView = findViewById(R.id.txtTestingInformationCounter);

        mapBuffer = map;

        if(total > count)
        {
            textView.setText(count + "/" + total + " selected - not complete");
            textView.setTextColor(Color.RED);
        }
        else if (total == count)
        {
            textView.setText(count + "/" + total + " selected - complete");
            textView.setTextColor(Color.rgb(0,155,119));
        }
        else
        {
            textView.setText("There is a problem, please contact the system administrator");
            textView.setTextColor(Color.RED);
        }
    }

    class TestingPhaseListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<_SubPractice> subPractices;
        private HashMap<Integer, String> stateHolder;
        private RadioButtonClickListener radioButtonClickListener;

        public TestingPhaseListAdapter(Context context, List<_SubPractice> subPractices, RadioButtonClickListener listener)
        {
            this.context = context;
            this.subPractices = subPractices;
            this.radioButtonClickListener = listener;

            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            stateHolder = new HashMap<>();
        }
        @Override
        public int getCount() {
            return subPractices.size();
        }

        @Override
        public Object getItem(int i) {
            return subPractices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.item_sub_practice, null);

            holder.subPracticeDesc = rowView.findViewById(R.id.txtSubDescription);
            holder.impl1 = rowView.findViewById(R.id.imp1);
            holder.impl2 = rowView.findViewById(R.id.imp2);
            holder.impl3 = rowView.findViewById(R.id.imp3);
            holder.impl4 = rowView.findViewById(R.id.imp4);
            holder.impl5 = rowView.findViewById(R.id.imp5);

            holder.subPracticeDesc.setText(subPractices.get(i).getDescription());
            CompoundButton.OnCheckedChangeListener checkChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    String answer = compoundButton.getText().toString();
                    if (isChecked) {
                        int subPracticeId = subPractices.get(i).getSub_practice_id();
                        if(stateHolder.containsKey(subPracticeId))
                            stateHolder.replace(subPracticeId, answer);
                        else
                            stateHolder.put(subPracticeId, answer);
                    }
                    int count = stateHolder.size();
                    radioButtonClickListener.onRadioButtonClicked(count, stateHolder);
                }
            };

            holder.impl1.setOnCheckedChangeListener(checkChangeListener);
            holder.impl2.setOnCheckedChangeListener(checkChangeListener);
            holder.impl3.setOnCheckedChangeListener(checkChangeListener);
            holder.impl4.setOnCheckedChangeListener(checkChangeListener);
            holder.impl5.setOnCheckedChangeListener(checkChangeListener);

            state(subPractices.get(i).getSub_practice_id(), holder);

            return rowView;
        }
        private void state(int position, Holder holder)
        {
            if (Objects.equals(stateHolder.get(position), "1")) {
                holder.impl1.setChecked(true);
            } else if (Objects.equals(stateHolder.get(position), "2")) {
                holder.impl2.setChecked(true);
            } else if (Objects.equals(stateHolder.get(position), "3")) {
                holder.impl3.setChecked(true);
            } else if (Objects.equals(stateHolder.get(position), "4")) {
                holder.impl4.setChecked(true);
            } else if (Objects.equals(stateHolder.get(position), "5")) {
                holder.impl5.setChecked(true);
            }
        }
        class Holder
        {
            TextView subPracticeDesc;
            RadioButton impl1, impl2, impl3, impl4, impl5;
        }
    }
}