package com.yishak.smm_assessment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yishak.smm_assessment.R;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.List;

public class SubPracticeAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<_SubPractice> subPractices;
    public static ArrayList<String> selectedAnswers;

    public SubPracticeAdapter(Context context, List<_SubPractice> subPractices)
    {
        this.context = context;
        this.subPractices = subPractices;

        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < subPractices.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return subPractices.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
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
        holder.impl1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(i, "1");
                Log.i("Selected", String.valueOf(selectedAnswers.size()));
            }
        });
        holder.impl2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(i, "2");
            }
        });
        holder.impl3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(i, "3");
            }
        });
        holder.impl4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(i, "4");
            }
        });
        holder.impl5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    selectedAnswers.set(i, "5");
            }
        });
        return rowView;
    }
}

class Holder
{
    TextView subPracticeDesc;
    RelativeLayout relativeLayout;
    RadioButton impl1, impl2, impl3, impl4, impl5;
}
