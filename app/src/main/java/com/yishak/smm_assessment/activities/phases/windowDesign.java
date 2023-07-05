package com.yishak.smm_assessment.activities.phases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.common.Commons;
import com.yishak.smm_assessment.common.Shared;
import com.yishak.smm_assessment.interfaces.RadioButtonClickListener;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class windowDesign extends AppCompatActivity implements RadioButtonClickListener{

    private ListView designPhaseListView;
    private ExtendedFloatingActionButton btnNext;
    private DesignPhaseListAdapter adapter;
    private HashMap<Integer, String> mapBuffer;
    private List<_SubPractice> designPhaseSubPractices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_design);

        init();
        inflateList(designPhaseSubPractices);
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbDesignPhase);
        toolbar.setTitle("Design Phase");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        designPhaseSubPractices = Commons.designPhase.getPractices().get(0).getSubPractices();

        designPhaseListView = findViewById(R.id.lstDesignPhase);
        btnNext = findViewById(R.id.fabDesignNext);

        mapBuffer = new HashMap<>();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate();
                if (isOk)
                {
                    new MaterialAlertDialogBuilder(windowDesign.this, R.style.AlertDialogTheme)
                            .setTitle("Confirmation")
                            .setMessage("Please ensure that your choices are definitive.")
                            .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Shared.createBuffer(mapBuffer, designPhaseSubPractices, "dE");
                                    Intent intent = new Intent(windowDesign.this, windowImplementation.class);
                                    startActivity(intent);
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
    private void inflateList(List<_SubPractice> subPractices)
    {
        adapter = new DesignPhaseListAdapter(getApplicationContext(), subPractices, this);
        designPhaseListView.setAdapter(adapter);
    }

    @Override
    public void onRadioButtonClicked(int count, HashMap<Integer, String> map) {
        int total = designPhaseSubPractices.size();
        TextView textView = findViewById(R.id.txtDesignInformationCounter);

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

    class DesignPhaseListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<_SubPractice> subPractices;
        private HashMap<Integer, String> stateHolder;
        private RadioButtonClickListener radioButtonClickListener;

        public DesignPhaseListAdapter(Context context, List<_SubPractice> subPractices, RadioButtonClickListener listener)
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