package com.yishak.smm_assessment.activities.phases;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.yishak.smm_assessment.MainActivity;
import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.common.Shared;
import com.yishak.smm_assessment.interfaces.RadioButtonClickListener;
import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._Phase;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowRequirements extends AppCompatActivity implements RadioButtonClickListener{
    public static ArrayList<_Phase> phaseList;
    public static _Phase requirementEngineeringPhase;
    public static _Phase designPhase;
    public static _Phase implementationPhase;
    public static _Phase deploymentPhase;
    public static _Phase testingPhase;
    private ListView subPracticeListView;
    private RequirementsPhaseListAdapter adapter;
    private ExtendedFloatingActionButton btnNext;
    private TextView counter;
    private HashMap<Integer, String> mapBuffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_sub_practices);

        init();
        details();
    }

    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbSubPractices);
        toolbar.setTitle("Requirements Phase");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        subPracticeListView = findViewById(R.id.lstSubPractices);
        btnNext = findViewById(R.id.fabSubNext);

        counter = findViewById(R.id.txtSubInformationCounter);
        mapBuffer = new HashMap<>();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOk = validate();
                if (isOk)
                {
                    new MaterialAlertDialogBuilder(windowRequirements.this, R.style.AlertDialogTheme)
                            .setTitle("Confirmation")
                            .setMessage("Please ensure that your choices are definitive.")
                            .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Shared.createBuffer(mapBuffer, requirementEngineeringPhase.getPractices().get(0).getSubPractices(), "rE");
                                    Intent intent = new Intent(windowRequirements.this, windowDesign.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(windowRequirements.this, MainActivity.class);
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
        adapter = new RequirementsPhaseListAdapter(getApplicationContext(), subPractices, this);
        subPracticeListView.setAdapter(adapter);
    }

    @Override
    public void onRadioButtonClicked(int count, HashMap<Integer, String> map)
    {
        int total = requirementEngineeringPhase.getPractices().get(0).getSubPractices().size();
        TextView textView = findViewById(R.id.txtSubInformationCounter);

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

    class RequirementsPhaseListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<_SubPractice> subPractices;
        public HashMap<Integer, String> stateHolder;
        private RadioButtonClickListener radioButtonClickListener;

        public RequirementsPhaseListAdapter(Context context, List<_SubPractice> subPractices, RadioButtonClickListener listener)
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
                    int count = getSelectedRadioButtonCount(stateHolder);
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
        private int getSelectedRadioButtonCount(HashMap<Integer, String> hashMap) {
            return stateHolder.size();
        }
        class Holder
        {
            TextView subPracticeDesc;
            RadioButton impl1, impl2, impl3, impl4, impl5;
        }
    }
}